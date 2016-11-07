package com.rottentomatoes.movieapi.domain.repository;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.type.TypeFactory;
import org.springframework.stereotype.Component;

import com.rottentomatoes.movieapi.domain.meta.RelatedMetaDataInformation;
import com.rottentomatoes.movieapi.domain.model.AudienceReview;
import com.rottentomatoes.movieapi.domain.model.Movie;

import io.katharsis.queryParams.RequestParams;
import io.katharsis.repository.MetaRepository;
import io.katharsis.repository.RelationshipRepository;
import io.katharsis.response.MetaDataEnabledList;
import io.katharsis.response.MetaInformation;

import static com.rottentomatoes.movieapi.utils.RepositoryUtils.getLimit;
import static com.rottentomatoes.movieapi.utils.RepositoryUtils.getOffset;

@SuppressWarnings("rawtypes")
@Component
public class MovieToAudienceReviewRepository extends AbstractRepository implements RelationshipRepository<Movie, String, AudienceReview, String>, MetaRepository {


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
    public AudienceReview findOneTarget(String sourceId, String fieldName, RequestParams requestParams) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public MetaDataEnabledList<AudienceReview> findManyTargets(String movieId, String fieldName, RequestParams requestParams) {
        Map<String, Object> selectParams = new HashMap<>();
        MetaDataEnabledList<AudienceReview> reviewList = null;

        selectParams.put("movie_id", movieId);
        selectParams.put("limit", getLimit(fieldName, requestParams));
        selectParams.put("offset", getOffset(fieldName, requestParams));

          PreEmsClient preEmsClient = new PreEmsClient<List<AudienceReview>>(preEmsConfig);
          List<AudienceReview> rawReviewList = (List<AudienceReview>)preEmsClient.callPreEmsList(selectParams, "movie", movieId + "/audience-review", TypeFactory.defaultInstance().constructCollectionType(List.class,  AudienceReview.class));
          reviewList = new MetaDataEnabledList(rawReviewList);

          return reviewList;
    }

    @Override
    public MetaInformation getMetaInformation(Object root, Iterable resources, RequestParams requestParams, Serializable castedResourceId) {
        Map<String, Object> selectParams = new HashMap<>();
        RelatedMetaDataInformation metaData = new RelatedMetaDataInformation();
        metaData.setTotalCount(0);
        
        if (root instanceof RelationshipRepository) {
            metaData.setRequestParams(requestParams);
        }
        return metaData;
    }
}
