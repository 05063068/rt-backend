package com.rottentomatoes.movieapi.domain.repository.critic;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.rottentomatoes.movieapi.domain.meta.RelatedMetaDataInformation;
import com.rottentomatoes.movieapi.domain.model.Critic;

import com.rottentomatoes.movieapi.domain.repository.AbstractRepository;
import com.rottentomatoes.movieapi.domain.repository.ems.EmsClient;
import com.rottentomatoes.movieapi.utils.SearchUtils;
import io.katharsis.queryParams.RequestParams;
import io.katharsis.repository.MetaRepository;
import io.katharsis.repository.ResourceRepository;
import io.katharsis.response.MetaDataEnabledList;
import io.katharsis.response.MetaInformation;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.rottentomatoes.movieapi.utils.SearchUtils.loadSearchMeta;

@Component
public class CriticRepository extends AbstractRepository implements ResourceRepository<Critic, String>, MetaRepository {

    @Override
    public <S extends Critic> S save(S entity) {
        return null;
    }

    @Override
    public void delete(String id) {

    }

    @Override
    public Critic findOne(String id, RequestParams requestParams) {

        Map<String, Object> selectParams = new HashMap<>();

        EmsClient emsClient = emsRouter.fetchEmsClientForEndpoint(this.getClass());
        Critic critic = (Critic)emsClient.callEmsEntity(selectParams, "critic", id, Critic.class);
        return critic;
    }

    @Override
    public Iterable<Critic> findAll(RequestParams requestParams) {
        // Return list of all critics. Allow filter by last name

        EmsClient emsClient = emsRouter.fetchEmsClientForEndpoint(this.getClass());

        Map<String, Object> selectParams = new HashMap<>();
        MetaDataEnabledList<Critic> critics = null;

        if(requestParams.getFilters() != null) {
            if (requestParams.getFilters().containsKey("index-search")) {
                List<Long> criticIds = new ArrayList<>();
                JsonNode json;

                if (requestParams.getFilters().get("index-search") instanceof Map) {
                    json = SearchUtils.callSearchService("critics", requestParams, "index-search");

                    ArrayNode resultArr = (ArrayNode) json.path("results");
                    for (JsonNode movie : resultArr) {
                        criticIds.add(Long.parseLong(movie.path("id").textValue()));
                    }
                    selectParams.put("ids", StringUtils.join(criticIds,","));
                }
                else {
                    throw new IllegalArgumentException("Invalid search query.");
                }
                if(criticIds.size() > 0){
                    critics = new MetaDataEnabledList<>((List<Critic>) emsClient.callEmsList(selectParams, "critic", null, TypeFactory.defaultInstance().constructCollectionType(List.class, Critic.class)));
                    critics.setMetaInformation(loadSearchMeta(json, requestParams));
                }
            }
            else {
                if (requestParams.getFilters().containsKey("search")) {
                    selectParams.put("search", "%" + requestParams.getFilters().get("search") + "%");
                }
                if (requestParams.getFilters().containsKey("initial")) {
                    selectParams.put("initial", requestParams.getFilters().get("initial") + "%");
                }
                if (requestParams.getFilters().containsKey("lastInitial")) {
                    selectParams.put("lastInitial", requestParams.getFilters().get("lastInitial") + "%");
                }
                if (requestParams.getFilters().containsKey("legacy")) {
                    selectParams.put("legacy", requestParams.getFilters().get("legacy"));
                }
                if (requestParams.getFilters().containsKey("tmApproved")) {
                    selectParams.put("tmApproved", requestParams.getFilters().get("tmApproved"));
                }
                selectParams.put("limit", getLimit("", requestParams));
                selectParams.put("offset", getOffset("", requestParams));

                critics = new MetaDataEnabledList<>((List<Critic>) emsClient.callEmsList(selectParams, "critic", null, TypeFactory.defaultInstance().constructCollectionType(List.class,  Critic.class)));
                critics.setMetaInformation((RelatedMetaDataInformation) emsClient.callEmsEntity(selectParams, "critic", "/meta", RelatedMetaDataInformation.class));
            }
        }

        return critics;
    }

    @Override
    public Iterable<Critic> findAll(Iterable<String> ids, RequestParams requestParams) {
        return null;
    }

    @Override
    public MetaInformation getMetaInformation(Object o, Iterable iterable, RequestParams requestParams, Serializable s) {
        return null;
    }

}
