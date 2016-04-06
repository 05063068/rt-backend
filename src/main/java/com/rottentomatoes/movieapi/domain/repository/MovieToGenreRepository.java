package com.rottentomatoes.movieapi.domain.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.rottentomatoes.movieapi.domain.model.Genre;
import com.rottentomatoes.movieapi.domain.model.Movie;

import io.katharsis.queryParams.RequestParams;
import io.katharsis.repository.RelationshipRepository;

@Component
public class MovieToGenreRepository extends AbstractRepository implements RelationshipRepository<Movie, String, Genre, String> {

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
    public Genre findOneTarget(String id, String fieldName, RequestParams requestParams) {
        return null;
    }

    @Override
    public Iterable<Genre> findManyTargets(String movieId, String fieldName, RequestParams requestParams) {
        Map<String, Object> selectParams = new HashMap<>();
        selectParams.put("movie_id", movieId);
        selectParams.put("limit", getLimit(fieldName, requestParams));

        List<Genre> genreList = sqlSession.selectList("com.rottentomatoes.movieapi.mappers.GenreMapper.selectGenresForMovie", selectParams);
        return genreList;
    }
}
