package com.rottentomatoes.movieapi.domain.repository.account;

import com.rottentomatoes.movieapi.domain.apicalldelegators.account.SignupApiCall;
import com.rottentomatoes.movieapi.domain.model.account.Signup;
import com.rottentomatoes.movieapi.domain.repository.AbstractRepository;
import io.katharsis.queryParams.RequestParams;
import io.katharsis.repository.ResourceRepository;
import org.springframework.stereotype.Component;

@Component
public class SignupRepository extends AbstractRepository implements ResourceRepository<Signup, String> {

    private Signup resource;

    @Override
    public <S extends Signup> S save(S entity) {
        resource = entity;
        return entity;
    }

    @Override
    public void delete(String id) {

    }

    @Override
    public Signup findOne(String id, RequestParams requestParams) {
        if (resource == null || id != null) {
            // TODO throw error for performing GET request
            return null;
        }
        return (new SignupApiCall(environment, resource)).process();
    }

    @Override
    public Iterable<Signup> findAll(RequestParams requestParams) {
        return null;
    }

    @Override
    public Iterable<Signup> findAll(Iterable<String> ids, RequestParams requestParams) {
        // TODO Auto-generated method stub
        return null;
    }

}
