package com.rottentomatoes.movieapi.domain.repository;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

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

        if(requestParams.getFilters() != null && requestParams.getFilters().containsKey(CRITIC_TYPE) && ((String) requestParams.getFilters().get(CRITIC_TYPE)).equalsIgnoreCase(TOP_CRITICS)) {
            reviewList = new MetaDataEnabledList<>(sqlSession.selectList("com.rottentomatoes.movieapi.mappers.ReviewMapper.selectTopCriticReviewsForMovie", selectParams));
        } else {
            reviewList = new MetaDataEnabledList<>(sqlSession.selectList("com.rottentomatoes.movieapi.mappers.ReviewMapper.selectAllReviewsForMovie", selectParams));
        }

        return reviewList;
	}

    @Override
    public MetaInformation getMetaInformation(Object root, Iterable resources, RequestParams requestParams, Serializable castedResourceId) {
        Map<String, Object> selectParams = new HashMap<>();
        RelatedMetaDataInformation metaData;

        selectParams.put("movie_id", castedResourceId);

        if(requestParams.getFilters() != null && requestParams.getFilters().containsKey(CRITIC_TYPE) && ((String) requestParams.getFilters().get(CRITIC_TYPE)).equalsIgnoreCase(TOP_CRITICS)) {
            metaData = sqlSession.selectOne("com.rottentomatoes.movieapi.mappers.ReviewMapper.selectTopCriticReviewCountForMovie", selectParams);
        } else {
            metaData = sqlSession.selectOne("com.rottentomatoes.movieapi.mappers.ReviewMapper.selectAllReviewCountForMovie", selectParams);
        }

        if(root instanceof RelationshipRepository) {
            metaData.setRequestParams(requestParams);
        }

        return metaData;
    }
}
