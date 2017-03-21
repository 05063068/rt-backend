package com.rottentomatoes.movieapi.domain.repository.account;

import com.rottentomatoes.movieapi.domain.apicalldelegators.account.LoginApiCall;
import com.rottentomatoes.movieapi.domain.model.account.Login;
import com.rottentomatoes.movieapi.domain.repository.AbstractRepository;
import io.katharsis.queryParams.RequestParams;
import io.katharsis.repository.ResourceRepository;
import org.springframework.stereotype.Component;

@Component
public class LoginRepository extends AbstractRepository implements ResourceRepository<Login, String> {

    private Login resource;

    @Override
    public <S extends Login> S save(S entity) {
        resource = entity;
        return entity;
    }

    @Override
    public void delete(String id) {

    }

    @Override
    public Login findOne(String id, RequestParams requestParams) {
        if (resource == null || id != null) {
            // TODO throw error for performing GET request
            return null;
        }
        return (new LoginApiCall(environment, resource)).process();
    }

    @Override
    public Iterable<Login> findAll(RequestParams requestParams) {
        return null;
    }

    @Override
    public Iterable<Login> findAll(Iterable<String> ids, RequestParams requestParams) {
        // TODO Auto-generated method stub
        return null;
    }

}
