package com.rottentomatoes.movieapi.domain.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.rottentomatoes.movieapi.domain.model.Franchise;
import com.rottentomatoes.movieapi.utils.SearchUtils;
import io.katharsis.response.MetaDataEnabledList;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import io.katharsis.queryParams.RequestParams;
import io.katharsis.repository.ResourceRepository;
import static com.rottentomatoes.movieapi.utils.SearchUtils.loadSearchMeta;

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

        EmsClient emsClient = emsRouter.fetchEmsClientForEndpoint(this.getClass());
        return (Franchise) emsClient.callEmsEntity(selectParams, "franchise", id, Franchise.class);
    }

    @Override
    public Iterable<Franchise> findAll(RequestParams requestParams) {
        EmsClient emsClient = emsRouter.fetchEmsClientForEndpoint(this.getClass());
        Map<String, Object> selectParams = new HashMap<>();
        MetaDataEnabledList<Franchise> franchises = null;

        if (requestParams.getFilters() != null && requestParams.getFilters().get("search") != null) {
            List<Long> franchiseIds = new ArrayList<>();
            JsonNode json;

            if (requestParams.getFilters().get("search") instanceof Map) {
                json = SearchUtils.callSearchService("franchises", requestParams);

                ArrayNode resultArr = (ArrayNode) json.path("results");
                franchiseIds = new ArrayList<>();
                for (JsonNode movie : resultArr) {
                    franchiseIds.add(Long.parseLong(movie.path("id").textValue()));
                }
            } else {
                throw new IllegalArgumentException("Invalid search query.");
            }

            //  Hydrate results
            if (franchiseIds.size() > 0) {
                franchises = new MetaDataEnabledList<>((List<Franchise>) emsClient.callEmsList(selectParams, "franchise", StringUtils.join(franchiseIds, ","), TypeFactory.defaultInstance().constructCollectionType(List.class, Franchise.class)));
                franchises.setMetaInformation(loadSearchMeta(json, requestParams));
            }
        }
        else {
            franchises = new MetaDataEnabledList<>(new ArrayList<>());
        }

        return franchises;
    }

    @Override
    public Iterable<Franchise> findAll(Iterable<String> ids, RequestParams requestParams) {
        // TODO Auto-generated method stub
        return null;
    }

}
