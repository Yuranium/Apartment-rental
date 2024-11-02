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
import ru.yuriy.propertyrental.models.entity.Apartment;
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
    public List<Apartment> getSearchApartments(ApartmentSearch apartmentSearch)
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

        SearchResponse<ApartmentES> response = searchClient.search(searchRequest, ApartmentES.class);
        response.hits().hits().forEach(hit -> System.out.println(hit.source()));
        return null;
    }
    private Supplier<Query> createSupplierAutoSuggest(String partialApartmentName)
    {
        return () -> Query.of(q -> q.match(new MatchQuery.Builder()
                .field("name")
                .query(partialApartmentName)
                .analyzer("standard")
                .fuzziness("AUTO")
                .build()));
    }

    @SneakyThrows
    private SearchResponse<ApartmentES> autoSuggestApartment(String partialApartmentName)
    {
        Supplier<Query> supplier = createSupplierAutoSuggest(partialApartmentName);
        return searchClient.search(s -> s.index("apartment")
                .query(supplier.get()), ApartmentES.class);
    }

    public List<ApartmentES> searchApartmentResult(String partialApartmentName)
    {
        return autoSuggestApartment(partialApartmentName)
                .hits()
                .hits()
                .stream()
                .map(Hit::source)
                .collect(Collectors.toList());
    }
}