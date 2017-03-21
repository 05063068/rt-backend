package com.rottentomatoes.movieapi.domain.repository.vanity;

import com.rottentomatoes.movieapi.domain.model.PersonVanityToken;
import com.rottentomatoes.movieapi.domain.repository.AbstractRepository;
import com.rottentomatoes.movieapi.domain.clients.ems.EmsClient;
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
        EmsClient emsClient = emsRouter.fetchEmsClientForEndpoint(this.getClass());
        PersonVanityToken token = (PersonVanityToken) emsClient.callEmsEntity(selectParams, "person-vanity-token", personVanityToken, PersonVanityToken.class);
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
