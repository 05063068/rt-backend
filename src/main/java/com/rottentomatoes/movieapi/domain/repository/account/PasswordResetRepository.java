package com.rottentomatoes.movieapi.domain.repository.account;

import com.rottentomatoes.movieapi.domain.apicalldelegators.account.PasswordResetApiCall;
import com.rottentomatoes.movieapi.domain.model.account.PasswordReset;
import com.rottentomatoes.movieapi.domain.repository.AbstractRepository;
import io.katharsis.queryParams.RequestParams;
import io.katharsis.repository.ResourceRepository;
import org.springframework.stereotype.Component;

@Component
public class PasswordResetRepository extends AbstractRepository implements ResourceRepository<PasswordReset, String> {

    private PasswordReset resource;

    @Override
    public <S extends PasswordReset> S save(S entity) {
        resource = entity;
        return entity;
    }

    @Override
    public void delete(String id) {

    }

    @Override
    public PasswordReset findOne(String id, RequestParams requestParams) {
        if (resource == null || id != null) {
            // TODO throw error for performing GET request
            return null;
        }
        return (new PasswordResetApiCall(environment, resource)).process();
    }

    @Override
    public Iterable<PasswordReset> findAll(RequestParams requestParams) {
        return null;
    }

    @Override
    public Iterable<PasswordReset> findAll(Iterable<String> ids, RequestParams requestParams) {
        // TODO Auto-generated method stub
        return null;
    }

}
