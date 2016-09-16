package com.rottentomatoes.movieapi.domain.repository;

import com.rottentomatoes.movieapi.domain.model.PersonVanityToken;
import io.katharsis.queryParams.RequestParams;
import io.katharsis.repository.ResourceRepository;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class PersonVanityTokenRepository extends AbstractRepository implements ResourceRepository<PersonVanityToken, String> {

    @Override
    public <S extends PersonVanityToken> S save(S entity) {
        return null;
    }

    @Override
    public void delete(String aLong) {
    }

    @Override
    public PersonVanityToken findOne(String personVanityToken, RequestParams requestParams) {
        Map<String, Object> selectParams = new HashMap<>();
        PreEmsClient preEmsClient = new PreEmsClient(preEmsConfig);
        PersonVanityToken token = (PersonVanityToken) preEmsClient.callPreEmsEntity(selectParams, "person-vanity-token", personVanityToken, PersonVanityToken.class);
        return token;
    }

    @Override

    public Iterable<PersonVanityToken> findAll(RequestParams requestParams) {
        return null;
    }

    @Override
    public Iterable<PersonVanityToken> findAll(Iterable<String> ids, RequestParams requestParams) {
        // TODO Auto-generated method stub
        return null;
    }
}
