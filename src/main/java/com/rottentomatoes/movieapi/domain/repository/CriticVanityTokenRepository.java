package com.rottentomatoes.movieapi.domain.repository;

import com.rottentomatoes.movieapi.domain.model.CriticVanityToken;
import io.katharsis.queryParams.RequestParams;
import io.katharsis.repository.ResourceRepository;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CriticVanityTokenRepository extends AbstractRepository implements ResourceRepository<CriticVanityToken, String> {

    @Override
    public <S extends CriticVanityToken> S save(S entity) {
        return null;
    }

    @Override
    public void delete(String aLong) {
    }

    @Override
    public CriticVanityToken findOne(String criticVanityToken, RequestParams requestParams) {
        Map<String, Object> selectParams = new HashMap<>();
        EmsClient emsClient = emsConfig.fetchEmsClientForEndpoint("critic-vanity-token");
        CriticVanityToken token = (CriticVanityToken) emsClient.callEmsEntity(selectParams, "critic-vanity-token", criticVanityToken, CriticVanityToken.class);
        return token;
    }

    @Override

    public Iterable<CriticVanityToken> findAll(RequestParams requestParams) {
        return null;
    }

    @Override
    public Iterable<CriticVanityToken> findAll(Iterable<String> ids, RequestParams requestParams) {
        // TODO Auto-generated method stub
        return null;
    }
}
