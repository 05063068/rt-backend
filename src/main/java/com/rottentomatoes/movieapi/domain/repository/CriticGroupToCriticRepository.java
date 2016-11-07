package com.rottentomatoes.movieapi.domain.repository;

import com.fasterxml.jackson.databind.type.TypeFactory;
import com.rottentomatoes.movieapi.domain.model.CriticGroup;
import com.rottentomatoes.movieapi.domain.model.Critic;
import io.katharsis.queryParams.RequestParams;
import io.katharsis.repository.RelationshipRepository;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.rottentomatoes.movieapi.utils.RepositoryUtils.getLimit;
import static com.rottentomatoes.movieapi.utils.RepositoryUtils.getOffset;

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
        EmsClient emsClient = emsConfig.fetchEmsClientForEndpoint("critic-group");
        Map<String, Object> selectParams = new HashMap<>();
        selectParams.put("limit", getLimit(fieldName, requestParams));
        selectParams.put("offset", getOffset(fieldName, requestParams));

        return (Iterable<Critic>) emsClient.callEmsList(selectParams, "critic-group", criticGroupId + "/critic", TypeFactory.defaultInstance().constructCollectionType(List.class,  Critic.class));

    }

}
