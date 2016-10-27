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

import static com.rottentomatoes.movieapi.utils.RepositoryUtils.getLimit;
import static com.rottentomatoes.movieapi.utils.RepositoryUtils.getOffset;

@SuppressWarnings("rawtypes")
@Component
public class CriticToReviewRepository extends AbstractRepository implements RelationshipRepository<Critic, String, Review, String>, MetaRepository {

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

        if(requestParams.getFilters() != null) {
            // order of the reviews, can be one of "best" or "worst"
            if (requestParams.getFilters().containsKey("order")) {
                selectParams.put("order", requestParams.getFilters().get("order"));
            }
            // Accepted category filter values are "movie", "dvd", or "quick"
            if (requestParams.getFilters().containsKey("category")) {
                selectParams.put("category", requestParams.getFilters().get("category"));
            }
            // Accepted category filter values are "fresh" or "rotten"
            if (requestParams.getFilters().containsKey("score")) {
                selectParams.put("score", requestParams.getFilters().get("score"));
            }
        }

        selectParams.put("limit", getLimit(fieldName, requestParams));
        selectParams.put("offset", getOffset(fieldName, requestParams));

        EmsClient emsClient = emsRouter.fetchEmsClientForEndpoint(this.getClass());
        return (Iterable<Review>) emsClient.callEmsList(selectParams, "critic", criticId + "/review", TypeFactory.defaultInstance().constructCollectionType(List.class,  Review.class));
    }


    @Override
    public MetaInformation getMetaInformation(Object o, Iterable iterable, RequestParams requestParams, Serializable criticId) {
        RelatedMetaDataInformation metaData;
        Map<String, Object> selectParams = new HashMap<>();

        if (requestParams.getFilters() != null) {
            if (requestParams.getFilters().containsKey("category")) {
                selectParams.put("category", requestParams.getFilters().get("category"));
            }
            if (requestParams.getFilters().containsKey("score")) {
                selectParams.put("score", requestParams.getFilters().get("score"));
            }
        }
        EmsClient emsClient = emsRouter.fetchEmsClientForEndpoint(this.getClass());
        metaData = (RelatedMetaDataInformation) emsClient.callEmsEntity(selectParams, "critic", criticId.toString() + "/review/meta", RelatedMetaDataInformation.class);
        metaData.setRequestParams(requestParams);
        return metaData;
    }
}
