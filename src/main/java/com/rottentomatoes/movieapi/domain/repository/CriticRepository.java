package com.rottentomatoes.movieapi.domain.repository;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.rottentomatoes.movieapi.domain.meta.RelatedMetaDataInformation;
import com.rottentomatoes.movieapi.domain.model.Critic;
import com.rottentomatoes.movieapi.search.SearchQuery;

import io.katharsis.queryParams.RequestParams;
import io.katharsis.repository.MetaRepository;
import io.katharsis.repository.ResourceRepository;
import io.katharsis.response.MetaInformation;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        PreEmsClient preEmsClient = new PreEmsClient<Critic>(preEmsConfig);
        Critic critic = (Critic)preEmsClient.callPreEmsEntity(selectParams, "critic", id, Critic.class);
        return critic;
    }

    @Override
    public Iterable<Critic> findAll(RequestParams requestParams) {
        // Return list of all critics. Allow filter by last name

        PreEmsClient preEmsClient = new PreEmsClient<List<Critic>>(preEmsConfig);

        Map<String, Object> selectParams = new HashMap<>();
        List<Critic> critics = null;

        if(requestParams.getFilters() != null) {
            if (requestParams.getFilters().containsKey("index-search")) {
                if (requestParams.getFilters().get("index-search") instanceof Map) {
                    Map<String, Object> searchObj = (Map<String, Object>) requestParams.getFilters().get("index-search");

                    SearchQuery q = new SearchQuery("critics", searchObj);
                    JsonNode json = q.execute();
                    ArrayNode resultArr = (ArrayNode) json.path("results");
                    List<Long> criticIds = new ArrayList<>();
                    for (JsonNode movie : resultArr) {
                        criticIds.add(Long.parseLong(movie.path("id").textValue()));
                    }
                    selectParams.put("ids", StringUtils.join(criticIds,","));
                } else {
                    throw new IllegalArgumentException("Invalid search query.");
                }

                critics =  (List<Critic>) preEmsClient.callPreEmsList(selectParams, "critic", null, TypeFactory.defaultInstance().constructCollectionType(List.class,  Critic.class));
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

                critics = (List<Critic>) preEmsClient.callPreEmsList(selectParams, "critic", null, TypeFactory.defaultInstance().constructCollectionType(List.class,  Critic.class));
            }
        }


        return critics;
    }

    @Override
    public Iterable<Critic> findAll(Iterable<String> ids, RequestParams requestParams) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public MetaInformation getMetaInformation(Object o, Iterable iterable, RequestParams requestParams, Serializable s) {
        RelatedMetaDataInformation metaData;
        Map<String, Object> selectParams = new HashMap<>();

        if(requestParams.getFilters() != null) {
            if(requestParams.getFilters().containsKey("search")) {
                selectParams.put("search", "%" + requestParams.getFilters().get("search") + "%");
            }
            if(requestParams.getFilters().containsKey("initial")) {
                selectParams.put("initial", requestParams.getFilters().get("initial") + "%");
            }
            if(requestParams.getFilters().containsKey("lastInitial")){
                selectParams.put("lastInitial", requestParams.getFilters().get("lastInitial") + "%");
            }
            if(requestParams.getFilters().containsKey("legacy")){
                selectParams.put("legacy", requestParams.getFilters().get("legacy"));
            }
            if(requestParams.getFilters().containsKey("tmApproved")){
                selectParams.put("tmApproved", requestParams.getFilters().get("tmApproved"));
            }
        }

        PreEmsClient preEmsClient = new PreEmsClient<RelatedMetaDataInformation>(preEmsConfig);
        metaData = (RelatedMetaDataInformation) preEmsClient.callPreEmsEntity(selectParams, "critic", "/meta", RelatedMetaDataInformation.class);
        return metaData;
    }

}
