package com.rottentomatoes.movieapi.domain.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.rottentomatoes.movieapi.domain.model.Critic;
import com.rottentomatoes.movieapi.domain.model.Franchise;
import com.rottentomatoes.movieapi.domain.model.Movie;
import com.rottentomatoes.movieapi.search.SearchQuery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import io.katharsis.queryParams.RequestParams;
import io.katharsis.repository.ResourceRepository;

@SuppressWarnings("rawtypes")
@Component
public class FranchiseRepository extends AbstractRepository implements ResourceRepository<Franchise, String> {

    @Override
    public <S extends Franchise> S save(S entity) {
        return null;
    }

    @Override
    public void delete(String id) {

    }

    @Override
    public Franchise findOne(String id, RequestParams requestParams) {

        Map<String, Object> selectParams = new HashMap<>();

        EmsClient emsClient = emsConfig.fetchEmsClientForEndpoint("franchise");
        return (Franchise) emsClient.callEmsEntity(selectParams, "franchise", id, Franchise.class);
    }

    @Override
    public Iterable<Franchise> findAll(RequestParams requestParams) {
        PreEmsClient preEmsClient = new PreEmsClient<Franchise>(preEmsConfig);
        Map<String, Object> selectParams = new HashMap<>();
        List<Franchise> franchises;
        List<Long> franchiseIds = new ArrayList<>();

        if (requestParams.getFilters() != null && requestParams.getFilters().get("search") != null) {

            if(requestParams.getFilters().get("search") instanceof Map){
                Map<String, Object> searchObj = (Map<String, Object>) requestParams.getFilters().get("search");

                SearchQuery q = new SearchQuery("franchises", searchObj);
                JsonNode json = q.execute();
                ArrayNode resultArr = (ArrayNode) json.path("results");
                franchiseIds = new ArrayList<>();
                for (JsonNode movie : resultArr) {
                    franchiseIds.add(Long.parseLong(movie.path("id").textValue()));
                }
                selectParams.put("ids", StringUtils.join(franchiseIds,","));
            }
            else{
                throw new IllegalArgumentException("Invalid search query.");
            }
        }

        //  Hydrate results
        if(franchiseIds.size() > 0) {
            franchises = (List<Franchise>) preEmsClient.callPreEmsList(selectParams, "franchise", null, TypeFactory.defaultInstance().constructCollectionType(List.class, Franchise.class));
        }
        else{
            franchises = new ArrayList<>();
        }
        return franchises;
    }

    @Override
    public Iterable<Franchise> findAll(Iterable<String> ids, RequestParams requestParams) {
        // TODO Auto-generated method stub
        return null;
    }

}
