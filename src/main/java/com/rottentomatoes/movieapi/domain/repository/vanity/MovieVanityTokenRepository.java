package com.rottentomatoes.movieapi.domain.repository.vanity;

import java.util.HashMap;
import java.util.Map;

import com.rottentomatoes.movieapi.domain.clients.ems.EmsClient;
import com.rottentomatoes.movieapi.domain.repository.AbstractRepository;
import org.springframework.stereotype.Component;

import com.rottentomatoes.movieapi.domain.model.MovieVanityToken;

import io.katharsis.queryParams.RequestParams;
import io.katharsis.repository.ResourceRepository;

@Component
public class MovieVanityTokenRepository extends AbstractRepository implements ResourceRepository<MovieVanityToken, String> {

    @Override
    public <S extends MovieVanityToken> S save(S entity) {
        return null;
    }

    @Override
    public void delete(String aLong) {
    }

    @Override
    public MovieVanityToken findOne(String vanityToken, RequestParams requestParams) {
        Map<String, Object> selectParams = new HashMap<>();
        EmsClient emsClient = emsRouter.fetchEmsClientForEndpoint(this.getClass());
        MovieVanityToken token = (MovieVanityToken) emsClient.callEmsEntity(selectParams, "vanity-token", vanityToken, MovieVanityToken.class);
        return token;
    }

    @Override

    public Iterable<MovieVanityToken> findAll(RequestParams requestParams) {
        return null;
    }

    @Override
    public Iterable<MovieVanityToken> findAll(Iterable<String> ids, RequestParams requestParams) {
        // TODO Auto-generated method stub
        return null;
    }
}
