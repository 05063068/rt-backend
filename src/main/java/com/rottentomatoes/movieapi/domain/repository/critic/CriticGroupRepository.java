package com.rottentomatoes.movieapi.domain.repository.critic;

import com.fasterxml.jackson.databind.type.TypeFactory;
import com.rottentomatoes.movieapi.domain.model.CriticGroup;
import com.rottentomatoes.movieapi.domain.repository.AbstractRepository;
import com.rottentomatoes.movieapi.domain.clients.ems.EmsClient;
import com.rottentomatoes.movieapi.utils.RepositoryUtils;
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
        EmsClient emsClient = emsRouter.fetchEmsClientForEndpoint(this.getClass());
        CriticGroup criticGroup = (CriticGroup)emsClient.callEmsEntity(selectParams, "critic-group", id, CriticGroup.class);
        return criticGroup;
    }

    @Override
    public Iterable<CriticGroup> findAll(RequestParams requestParams) {

        EmsClient emsClient = emsRouter.fetchEmsClientForEndpoint(this.getClass());
        Map<String, Object> selectParams = new HashMap<>();
        selectParams.put("limit", RepositoryUtils.getLimit("", requestParams));
        selectParams.put("offset", RepositoryUtils.getOffset("", requestParams));
        List<CriticGroup> criticGroups = (List<CriticGroup>) emsClient.callEmsList(selectParams, "critic-group", null, TypeFactory.defaultInstance().constructCollectionType(List.class,  CriticGroup.class));
        return criticGroups;

    }

    @Override
    public Iterable<CriticGroup> findAll(Iterable<String> ids, RequestParams requestParams) {
        // TODO Auto-generated method stub
        return null;
    }

}
