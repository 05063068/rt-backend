package com.rottentomatoes.movieapi.domain.repository;

import com.fasterxml.jackson.databind.type.TypeFactory;
import com.rottentomatoes.movieapi.domain.meta.RelatedMetaDataInformation;
import com.rottentomatoes.movieapi.domain.model.Critic;
import com.rottentomatoes.movieapi.domain.model.Review;
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
public class CriticToReviewRepository extends AbstractRepository implements RelationshipRepository<Critic, String, Review, String>,MetaRepository {

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
    public Review findOneTarget(String criticId, String fieldName, RequestParams requestParams) {
        return null;
    }

    @Override
    public Iterable<Review> findManyTargets(String criticId, String fieldName, RequestParams requestParams) {
        Map<String, Object> selectParams = new HashMap<>();
        selectParams.put("limit", getLimit(fieldName, requestParams));
        selectParams.put("offset", getOffset(fieldName, requestParams));

        PreEmsClient preEmsClient = new PreEmsClient<Iterable<Review>>(preEmsConfig);
        return (Iterable<Review>) preEmsClient.callPreEmsList(selectParams, "critic", criticId + "/review", TypeFactory.defaultInstance().constructCollectionType(List.class,  Review.class));
    }


    @Override
    public MetaInformation getMetaInformation(Object o, Iterable iterable, RequestParams requestParams, Serializable criticId) {
        RelatedMetaDataInformation metaData;
        Map<String, Object> selectParams = new HashMap<>();

        PreEmsClient preEmsClient = new PreEmsClient<RelatedMetaDataInformation>(preEmsConfig);
        metaData = (RelatedMetaDataInformation) preEmsClient.callPreEmsEntity(selectParams, "critic", criticId.toString() + "/review/meta", RelatedMetaDataInformation.class);
        metaData.setRequestParams(requestParams);
        return metaData;
    }
}
