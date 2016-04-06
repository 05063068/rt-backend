package com.rottentomatoes.movieapi.domain.repository;

import io.katharsis.queryParams.RequestParams;
import io.katharsis.repository.ResourceRepository;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.rottentomatoes.movieapi.domain.model.MovieCast;

@Component
public class MovieCastRepository extends AbstractRepository implements ResourceRepository<MovieCast, String> {

    @Override
    public void delete(String aString) {}

    @Override
    public <S extends MovieCast> S save(S arg0) {
        return null;
    }

    @Override
    public MovieCast findOne(String movieCastId, RequestParams requestParams) {
        Map<String, Object> selectParams = new HashMap<>();
        selectParams.put("movieCastId", movieCastId);
        MovieCast movieCast = sqlSession.selectOne("com.rottentomatoes.movieapi.mappers.MovieCastMapper.selectMovieCastById", selectParams);
        return movieCast;
    }

    @Override
    public Iterable<MovieCast> findAll(RequestParams requestParams) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Iterable<MovieCast> findAll(Iterable<String> ids, RequestParams requestParams) {
        // TODO Auto-generated method stub
        return null;
    }

}
