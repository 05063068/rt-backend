package com.rottentomatoes.movieapi.domain.repository;

import com.fasterxml.jackson.databind.type.TypeFactory;
import com.rottentomatoes.movieapi.domain.model.CriticGroup;
import io.katharsis.queryParams.RequestParams;
import io.katharsis.repository.ResourceRepository;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CriticGroupRepository extends AbstractRepository implements ResourceRepository<CriticGroup, String> {

    @Override
    public <S extends CriticGroup> S save(S entity) {
        return null;
    }

    @Override
    public void delete(String id) {

    }

    @Override
    public CriticGroup findOne(String id, RequestParams requestParams) {

        Map<String, Object> selectParams = new HashMap<>();
        EmsClient emsClient = emsConfig.fetchEmsClientForEndpoint("critic-group");
        CriticGroup criticGroup = (CriticGroup)emsClient.callEmsEntity(selectParams, "critic-group", id, CriticGroup.class);
        return criticGroup;
    }

    @Override
    public Iterable<CriticGroup> findAll(RequestParams requestParams) {

        EmsClient emsClient = emsConfig.fetchEmsClientForEndpoint("critic-group");
        Map<String, Object> selectParams = new HashMap<>();
        selectParams.put("limit", getLimit("", requestParams));
        selectParams.put("offset", getOffset("", requestParams));
        List<CriticGroup> criticGroups = (List<CriticGroup>) emsClient.callEmsList(selectParams, "critic-group", null, TypeFactory.defaultInstance().constructCollectionType(List.class,  CriticGroup.class));
        return criticGroups;

    }

    @Override
    public Iterable<CriticGroup> findAll(Iterable<String> ids, RequestParams requestParams) {
        // TODO Auto-generated method stub
        return null;
    }

}
