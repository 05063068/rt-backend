package com.rottentomatoes.movieapi.domain.repository.movie;

import com.rottentomatoes.movieapi.domain.repository.AbstractRepository;
import org.springframework.stereotype.Component;

import com.rottentomatoes.movieapi.domain.model.MovieList;

import io.katharsis.queryParams.RequestParams;
import io.katharsis.repository.ResourceRepository;

@Component
public class MovieListRepository extends AbstractRepository implements ResourceRepository<MovieList, String> {

    @Override
    public <S extends MovieList> S save(S entity) {
        return null;
    }

    @Override
    public void delete(String aLong) {
    }

    @Override
    public MovieList findOne(String listId, RequestParams requestParams) {
        MovieList list = new MovieList();
        list.setId(listId);
        return list;
    }

    @Override
    public Iterable<MovieList> findAll(RequestParams requestParams) {
        return null;
    }

    @Override
    public Iterable<MovieList> findAll(Iterable<String> ids, RequestParams requestParams) {
        return null;
    }
}
