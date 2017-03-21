package com.rottentomatoes.movieapi.domain.repository.critic;

import com.fasterxml.jackson.databind.type.TypeFactory;
import com.rottentomatoes.movieapi.domain.model.meta.RelatedMetaDataInformation;
import com.rottentomatoes.movieapi.domain.model.Critic;
import com.rottentomatoes.movieapi.domain.model.Review;
import com.rottentomatoes.movieapi.domain.repository.AbstractRepository;
import com.rottentomatoes.movieapi.domain.clients.ems.EmsClient;
import com.rottentomatoes.movieapi.utils.RepositoryUtils;
import io.katharsis.queryParams.RequestParams;
import io.katharsis.repository.MetaRepository;
import io.katharsis.repository.RelationshipRepository;
import io.katharsis.response.MetaDataEnabledList;
import io.katharsis.response.MetaInformation;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        selectParams.put("limit", RepositoryUtils.getLimit(fieldName, requestParams));
        selectParams.put("offset", RepositoryUtils.getOffset(fieldName, requestParams));

        EmsClient emsClient;
        MetaDataEnabledList<Review> reviewList;
        RelatedMetaDataInformation metaData;

        switch (fieldName) {
            case "tvReviews":
                emsClient = emsRouter.fetchEmsClientForPath("critic/" + fieldName);
                reviewList =  (MetaDataEnabledList<Review>) emsClient.callEmsIdList(selectParams, "critic", criticId + "/tv-reviews", "tv/review", TypeFactory.defaultInstance().constructCollectionType(MetaDataEnabledList.class, Review.class));
                break;
            case "reviews":
            default:
                if (requestParams.getFilters() != null) {
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
                emsClient = emsRouter.fetchEmsClientForEndpoint(this.getClass());
                reviewList =  (MetaDataEnabledList<Review>) emsClient.callEmsList(selectParams, "critic", criticId + "/review", TypeFactory.defaultInstance().constructCollectionType(MetaDataEnabledList.class, Review.class));
        }
        return reviewList;
    }

    @Override
    public MetaInformation getMetaInformation(Object root, Iterable resources, Serializable castedResourceId, String fieldName, RequestParams requestParams) {
        Map<String, Object> selectParams = new HashMap<>();
        EmsClient emsClient;
        RelatedMetaDataInformation metaData;

        switch (fieldName) {
            case "tvReviews":
                selectParams.put("limit", 10000);
                emsClient = emsRouter.fetchEmsClientForPath("critic/" + fieldName);
                List idList = (List<Integer>) emsClient.callEmsList(selectParams, "critic", castedResourceId.toString() + "/tv-reviews", TypeFactory.defaultInstance().constructCollectionType(List.class, Integer.class));

                metaData = new RelatedMetaDataInformation();
                if (idList != null) {
                    metaData.setTotalCount(idList.size());
                }
                break;
            case "reviews":
            default:
                if (requestParams.getFilters() != null) {
                    if (requestParams.getFilters().containsKey("category")) {
                        selectParams.put("category", requestParams.getFilters().get("category"));
                    }
                    if (requestParams.getFilters().containsKey("score")) {
                        selectParams.put("score", requestParams.getFilters().get("score"));
                    }
                }
                emsClient = emsRouter.fetchEmsClientForEndpoint(this.getClass());
                metaData = (RelatedMetaDataInformation) emsClient.callEmsEntity(selectParams, "critic", castedResourceId.toString() + "/review/meta", RelatedMetaDataInformation.class);
        }

        if (metaData != null && root instanceof RelationshipRepository) {
            metaData.setRequestParams(requestParams);
        }
        return metaData;
    }
}
