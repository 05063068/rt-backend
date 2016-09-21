package com.rottentomatoes.movieapi.domain.repository;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.rottentomatoes.movieapi.domain.model.Genre;

import io.katharsis.queryParams.RequestParams;
import io.katharsis.repository.ResourceRepository;

@Component
public class GenreRepository extends AbstractRepository implements ResourceRepository<Genre, String> {

    @Override
    public <S extends Genre> S save(S entity) {
        return null;
    }

    @Override
    public void delete(String id) {

    }

    @Override
    public Genre findOne(String id, RequestParams requestParams) {

        Map<String, Object> selectParams = new HashMap<>();

        PreEmsClient preEmsClient = new PreEmsClient(preEmsConfig);
        Genre genre = (Genre) preEmsClient.callPreEmsEntity(selectParams, "genre", id, Genre.class);
        return genre;
    }

    @Override
    public Iterable<Genre> findAll(RequestParams requestParams) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Iterable<Genre> findAll(Iterable<String> ids, RequestParams requestParams) {
        // TODO Auto-generated method stub
        return null;
    }

}
