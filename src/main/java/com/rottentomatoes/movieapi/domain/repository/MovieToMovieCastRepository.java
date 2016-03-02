package com.rottentomatoes.movieapi.domain.repository;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.rottentomatoes.movieapi.domain.meta.RelatedMetaDataInformation;
import com.rottentomatoes.movieapi.domain.model.Movie;
import com.rottentomatoes.movieapi.domain.model.MovieCast;

import io.katharsis.queryParams.RequestParams;
import io.katharsis.repository.MetaRepository;
import io.katharsis.repository.RelationshipRepository;
import io.katharsis.response.MetaDataEnabledList;
import io.katharsis.response.MetaInformation;

@SuppressWarnings("rawtypes")
@Component
public class MovieToMovieCastRepository extends AbstractRepository implements RelationshipRepository<Movie, String, MovieCast, String>, MetaRepository {

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
	public MovieCast findOneTarget(String sourceId, String fieldName, RequestParams requestParams) {
		return null;
	}

	@Override
	public MetaDataEnabledList<MovieCast> findManyTargets(String movieId, String fieldName, RequestParams requestParams) {
		Map<String, Object> selectParams = new HashMap<>();
		selectParams.put("movie_id", movieId);
        selectParams.put("limit", getLimit(fieldName, requestParams));
        selectParams.put("offset", getOffset(fieldName, requestParams));

        MetaDataEnabledList<MovieCast> personList = new MetaDataEnabledList(sqlSession.selectList("com.rottentomatoes.movieapi.mappers.MovieCastMapper.selectMovieCastForMovie", selectParams));
        return personList;
	}

    @Override
    public MetaInformation getMetaInformation(Object root, Iterable resources, RequestParams requestParams, Serializable castedResourceId) {
        Map<String, Object> selectParams = new HashMap<>();
        selectParams.put("movie_id", castedResourceId);

        RelatedMetaDataInformation metaData = sqlSession.selectOne("com.rottentomatoes.movieapi.mappers.MovieCastMapper.selectMovieCastCountForMovie", selectParams);
        if(root instanceof RelationshipRepository) {
            metaData.setRequestParams(requestParams);
        }

        return metaData;
    }
}
