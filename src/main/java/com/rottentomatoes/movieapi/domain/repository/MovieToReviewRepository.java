package com.rottentomatoes.movieapi.domain.repository;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.type.TypeFactory;
import com.rottentomatoes.movieapi.domain.meta.RelatedMetaDataInformation;
import com.rottentomatoes.movieapi.domain.model.Movie;
import com.rottentomatoes.movieapi.domain.model.Review;

import io.katharsis.queryParams.RequestParams;
import io.katharsis.repository.MetaRepository;
import io.katharsis.repository.RelationshipRepository;
import io.katharsis.response.MetaDataEnabledList;
import io.katharsis.response.MetaInformation;

@SuppressWarnings("rawtypes")
@Component
public class MovieToReviewRepository extends AbstractRepository implements RelationshipRepository<Movie, String, Review, String>, MetaRepository {

    private static final String CRITIC_TYPE = "criticType";
    private static final String TOP_CRITICS = "top";


    @Override
    public void addRelations(Movie arg0, Iterable<String> arg1, String arg2) {
    }

    @Override
    public void removeRelations(Movie arg0, Iterable<String> arg1, String arg2) {
    }

    @Override
    public void setRelation(Movie arg0, String arg1, String arg2) {
    }

    @Override
    public void setRelations(Movie arg0, Iterable<String> arg1, String arg2) {
    }

    @Override
    public Review findOneTarget(String sourceId, String fieldName, RequestParams requestParams) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public MetaDataEnabledList<Review> findManyTargets(String movieId, String fieldName, RequestParams requestParams) {
        Map<String, Object> selectParams = new HashMap<>();
        MetaDataEnabledList<Review> reviewList = null;

        selectParams.put("movie_id", movieId);
        selectParams.put("limit", getLimit(fieldName, requestParams));
        selectParams.put("offset", getOffset(fieldName, requestParams));
        selectParams.put("country", getCountry(requestParams).getCountryCode());

        if (requestParams.getFilters() != null && requestParams.getFilters().containsKey(CRITIC_TYPE)) {
            selectParams.put(CRITIC_TYPE, requestParams.getFilters().get(CRITIC_TYPE));
        }
        PreEmsClient preEmsClient = new PreEmsClient<List<Review>>(preEmsConfig);
        List<Review> rawReviewList = (List<Review>)preEmsClient.callPreEmsList(selectParams, "movie", movieId + "/review", TypeFactory.defaultInstance().constructCollectionType(List.class,  Review.class));
        return new MetaDataEnabledList(rawReviewList);
    }

    @Override
    public MetaInformation getMetaInformation(Object root, Iterable resources, RequestParams requestParams, Serializable castedResourceId) {
        Map<String, Object> selectParams = new HashMap<>();

        selectParams.put("country", getCountry(requestParams).getCountryCode());

        PreEmsClient preEmsClient = new PreEmsClient<RelatedMetaDataInformation>(preEmsConfig);
        RelatedMetaDataInformation metaData = null;
        if (requestParams.getFilters() != null && requestParams.getFilters().containsKey(CRITIC_TYPE) && ((String) requestParams.getFilters().get(CRITIC_TYPE)).equalsIgnoreCase(TOP_CRITICS)) {
            metaData = (RelatedMetaDataInformation) preEmsClient.callPreEmsEntity(selectParams, "movie", castedResourceId + "/top-critic-review/meta", RelatedMetaDataInformation.class);
        } else {
            metaData = (RelatedMetaDataInformation) preEmsClient.callPreEmsEntity(selectParams, "movie", castedResourceId + "/review/meta", RelatedMetaDataInformation.class);
        }

        if (root instanceof RelationshipRepository) {
            metaData.setRequestParams(requestParams);
        }

        return metaData;
    }
}
