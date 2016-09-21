package com.rottentomatoes.movieapi.domain.repository;

import com.fasterxml.jackson.databind.type.TypeFactory;

import com.rottentomatoes.movieapi.domain.model.Critic;
import io.katharsis.queryParams.RequestParams;
import io.katharsis.repository.ResourceRepository;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CriticRepository extends AbstractRepository implements ResourceRepository<Critic, String> {

    // Acceptable values for status filter
    private interface CriticStatus {
        String CURRENT = "current";
        String LEGACY = "legacy";
    }

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
            if(requestParams.getFilters().containsKey("initial")) {
                selectParams.put("initial", requestParams.getFilters().get("initial") + "%");
            }
            if(requestParams.getFilters().containsKey("lastInitial")){
                selectParams.put("lastInitial", requestParams.getFilters().get("lastInitial")+"%");
            }
            if(requestParams.getFilters().containsKey("legacy")){
                selectParams.put("legacy", requestParams.getFilters().get("legacy"));
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

}
