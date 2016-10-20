package com.rottentomatoes.movieapi.domain.repository;

import com.fasterxml.jackson.databind.type.TypeFactory;
import com.rottentomatoes.movieapi.domain.meta.RelatedMetaDataInformation;
import com.rottentomatoes.movieapi.domain.model.CriticGroup;
import com.rottentomatoes.movieapi.domain.model.Critic;

import io.katharsis.queryParams.RequestParams;
import io.katharsis.repository.MetaRepository;
import io.katharsis.repository.RelationshipRepository;
import io.katharsis.response.MetaInformation;

import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("rawtypes")
@Component
public class CriticToCriticGroupRepository extends AbstractRepository implements RelationshipRepository<Critic, String, CriticGroup, String>, MetaRepository {

    @Override
    public void setRelation(Critic critic, String s, String s2) {

    }

    @Override
    public void setRelations(Critic critic, Iterable<String> iterable, String s) {

    }

    @Override
    public void addRelations(Critic critic, Iterable<String> iterable, String s) {

    }

    @Override
    public void removeRelations(Critic critic, Iterable<String> iterable, String s) {

    }

    @Override
    public CriticGroup findOneTarget(String criticId, String fieldName, RequestParams requestParams) {
        return null;
    }

    @Override
    public Iterable<CriticGroup> findManyTargets(String criticId, String fieldName, RequestParams requestParams) {
        Map<String, Object> selectParams = new HashMap<>();
        selectParams.put("critic_id", criticId);
        selectParams.put("limit", getLimit(fieldName, requestParams));
        selectParams.put("offset", getOffset(fieldName, requestParams));

        EmsClient emsClient = emsConfig.fetchEmsClient("critic");
        return (Iterable<CriticGroup>) emsClient.callEmsList(selectParams, "critic", criticId + "/group", TypeFactory.defaultInstance().constructCollectionType(List.class,  CriticGroup.class));
    }

    @Override
    public MetaInformation getMetaInformation(Object o, Iterable iterable, RequestParams requestParams, Serializable criticId) {
        RelatedMetaDataInformation metaData;
        Map<String, Object> selectParams = new HashMap<>();

        EmsClient emsClient = emsConfig.fetchEmsClient("critic");
        metaData = (RelatedMetaDataInformation) emsClient.callEmsEntity(selectParams, "critic", criticId.toString() + "/group/meta", RelatedMetaDataInformation.class);
        metaData.setRequestParams(requestParams);
        return metaData;
    }
}
