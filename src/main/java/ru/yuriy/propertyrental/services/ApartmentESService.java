package ru.yuriy.propertyrental.services;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.*;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.json.JsonData;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import ru.yuriy.propertyrental.models.ApartmentSearch;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ApartmentESService
{
    private final ElasticsearchClient searchClient;

    @SneakyThrows
    public List<ApartmentSearch> getSearchApartments(ApartmentSearch apartmentSearch)
    {
        List<Query> mustQueries = new ArrayList<>();

        MatchQuery nameQuery = MatchQuery.of(m -> m
                .field("name")
                .query(apartmentSearch.getName())
                .fuzziness("AUTO")
        );
        mustQueries.add(Query.of(q -> q.match(nameQuery)));

        createTermQueryIfPossible(apartmentSearch, mustQueries);
        createRangeQueryIfPossible(apartmentSearch, mustQueries);

        BoolQuery boolQuery = BoolQuery.of(b -> b.must(mustQueries));
        SearchRequest searchRequest = SearchRequest.of(s -> s
                .index("apartment")
                .query(Query.of(q -> q.bool(boolQuery)))
        );

        return searchClient.search(searchRequest, ApartmentSearch.class)
                .hits()
                .hits()
                .stream()
                .map(Hit::source)
                .collect(Collectors.toList());
    }

    private void createTermQueryIfPossible(ApartmentSearch apartmentSearch, List<Query> queries)
    {
        if (apartmentSearch.getRoomCount() != null)
        {
            TermQuery termQuery = TermQuery.of(t -> t
                    .field("roomCount")
                    .value(apartmentSearch.getRoomCount())
            );
            queries.add(Query.of(q -> q.term(termQuery)));
        }

        if (apartmentSearch.getApartmentType() != null && !apartmentSearch.getApartmentType().isBlank())
        {
            TermQuery termQuery1 = TermQuery.of(t -> t
                    .field("apartmentType")
                    .value(apartmentSearch.getApartmentType().toUpperCase())
            );
            queries.add(Query.of(q -> q.term(termQuery1)));
        }
    }

    private void createRangeQueryIfPossible(ApartmentSearch apartmentSearch, List<Query> queries)
    {
        Double dailyPrice = apartmentSearch.getDailyPrice();
        if (dailyPrice != null)
        {
            RangeQuery priceQuery = RangeQuery.of(r -> r
                    .field("dailyPrice")
                    .lte(JsonData.of(dailyPrice))
            );
            queries.add(Query.of(q -> q.range(priceQuery)));
        }
    }

    @SneakyThrows
    public List<String> autocompleteQuery(String query)
    {
        SearchRequest searchRequest = SearchRequest.of(s -> s
                .index("apartment")
                .query(q -> q.matchPhrasePrefix(m -> m
                        .field("name")
                        .query(query)
                ))
                .size(10)
        );

        return searchClient.search(searchRequest, ApartmentSearch.class)
                .hits()
                .hits()
                .stream()
                .map(hit -> hit.source().getName())
                .collect(Collectors.toList());
    }
}