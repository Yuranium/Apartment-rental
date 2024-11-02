package ru.yuriy.propertyrental.services;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.RangeQuery;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.json.JsonData;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import ru.yuriy.propertyrental.models.ApartmentSearch;
import ru.yuriy.propertyrental.models.entity.ApartmentES;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ApartmentESService
{
    private final ElasticsearchClient searchClient;

    @SneakyThrows
    public List<ApartmentES> getSearchApartments(ApartmentSearch apartmentSearch)
    {
        MatchQuery nameQuery = MatchQuery.of(m -> m
                .field("name")
                .query(apartmentSearch.getName())
                .fuzziness("AUTO")
        );

        Double dailyPrice = apartmentSearch.getDailyPrice();
        RangeQuery priceQuery = RangeQuery.of(r -> r
                .field("dailyPrice")
                .lte(JsonData.of(dailyPrice == null ? Double.MAX_VALUE : dailyPrice))
        );

        BoolQuery boolQuery = BoolQuery.of(b -> b
                .must(Query.of(q -> q.match(nameQuery)))
                .must(Query.of(q -> q.range(priceQuery)))
        );

        SearchRequest searchRequest = SearchRequest.of(s -> s
                .index("apartment")
                .query(Query.of(q -> q.bool(boolQuery)))
        );

        return searchClient.search(searchRequest, ApartmentES.class)
                .hits()
                .hits()
                .stream()
                .map(Hit::source)
                .collect(Collectors.toList());
    }
}