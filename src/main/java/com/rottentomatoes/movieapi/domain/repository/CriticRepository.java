package com.rottentomatoes.movieapi.domain.repository;

import com.fasterxml.jackson.databind.type.TypeFactory;
import com.rottentomatoes.movieapi.domain.meta.RelatedMetaDataInformation;
import com.rottentomatoes.movieapi.domain.model.Critic;

import io.katharsis.queryParams.RequestParams;
import io.katharsis.repository.MetaRepository;
import io.katharsis.repository.ResourceRepository;
import io.katharsis.response.MetaInformation;

import org.springframework.stereotype.Component;

import java.io.Serializable;
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
        selectParams.put("limit", getLimit("", requestParams));
        selectParams.put("offset", getOffset("", requestParams));

        if(requestParams.getFilters() != null){
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

        List<Critic> critics = (List<Critic>) preEmsClient.callPreEmsList(selectParams, "critic", null, TypeFactory.defaultInstance().constructCollectionType(List.class,  Critic.class));
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

        metaData = sqlSession.selectOne("com.rottentomatoes.movieapi.mappers.CriticMapper.selectAllCriticsCount", selectParams);
        return metaData;
    }

}
