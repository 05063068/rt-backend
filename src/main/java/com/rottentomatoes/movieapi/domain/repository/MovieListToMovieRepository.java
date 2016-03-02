package com.rottentomatoes.movieapi.domain.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.rottentomatoes.movieapi.domain.model.Movie;
import com.rottentomatoes.movieapi.domain.model.MovieList;

import io.katharsis.queryParams.RequestParams;
import io.katharsis.repository.RelationshipRepository;

@Component
public class MovieListToMovieRepository extends AbstractRepository implements RelationshipRepository<MovieList, String, Movie, String> {
    
    @Override
    public void addRelations(MovieList arg0, Iterable<String> arg1, String arg2) {
    }

    @Override
    public void removeRelations(MovieList arg0, Iterable<String> arg1, String arg2) {
    }

    @Override
    public void setRelation(MovieList arg0, String arg1, String arg2) {
    }
    
    @Override
    public void setRelations(MovieList arg0, Iterable<String> arg1, String arg2) {
    }

	@Override
	public Movie findOneTarget(String id, String fieldName, RequestParams requestParams) {
		return null;
	}

	@Override
	public Iterable<Movie> findManyTargets(String movieId, String fieldName, RequestParams requestParams) {
        Map<String, Object> selectParams = new HashMap<>();
        selectParams.put("limit", getLimit(fieldName, requestParams));
        
        List<Movie> movieList = sqlSession.selectList("com.rottentomatoes.movieapi.mappers.MovieListMapper.selectBoxOfficeMovies", selectParams);
        return movieList;
	}
}
