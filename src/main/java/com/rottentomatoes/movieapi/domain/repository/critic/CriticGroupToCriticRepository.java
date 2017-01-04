package com.rottentomatoes.movieapi.domain.repository.critic;

import com.fasterxml.jackson.databind.type.TypeFactory;
import com.rottentomatoes.movieapi.domain.model.CriticGroup;
import com.rottentomatoes.movieapi.domain.model.Critic;
import com.rottentomatoes.movieapi.domain.repository.AbstractRepository;
import com.rottentomatoes.movieapi.domain.ems.EmsClient;
import com.rottentomatoes.movieapi.utils.RepositoryUtils;
import io.katharsis.queryParams.RequestParams;
import io.katharsis.repository.RelationshipRepository;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("rawtypes")
@Component
public class CriticGroupToCriticRepository extends AbstractRepository implements RelationshipRepository<CriticGroup, String, Critic, String> {

    @Override
    public void setRelation(CriticGroup criticGroup, String s, String s2) {

    }

    @Override
    public void setRelations(CriticGroup criticGroup, Iterable<String> iterable, String s) {

    }

    @Override
    public void addRelations(CriticGroup criticGroup, Iterable<String> iterable, String s) {

    }

    @Override
    public void removeRelations(CriticGroup criticGroup, Iterable<String> iterable, String s) {

    }

    @Override
    public Critic findOneTarget(String criticGroupId, String fieldName, RequestParams requestParams) {
        return null;
    }

    @Override
    public Iterable<Critic> findManyTargets(String criticGroupId, String fieldName, RequestParams requestParams) {
        EmsClient emsClient = emsRouter.fetchEmsClientForEndpoint(this.getClass());
        Map<String, Object> selectParams = new HashMap<>();
        selectParams.put("limit", RepositoryUtils.getLimit(fieldName, requestParams));
        selectParams.put("offset", RepositoryUtils.getOffset(fieldName, requestParams));

        return (Iterable<Critic>) emsClient.callEmsList(selectParams, "critic-group", criticGroupId + "/critic", TypeFactory.defaultInstance().constructCollectionType(List.class,  Critic.class));

    }

}
