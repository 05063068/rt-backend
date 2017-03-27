package com.rottentomatoes.movieapi.domain.repository.account;

import com.rottentomatoes.movieapi.domain.apicalldelegators.account.UserApiCall;
import com.rottentomatoes.movieapi.domain.model.account.User;
import com.rottentomatoes.movieapi.domain.repository.AbstractRepository;
import io.katharsis.queryParams.RequestParams;
import io.katharsis.repository.ResourceRepository;
import org.springframework.stereotype.Component;

@Component
public class UserRepository extends AbstractRepository implements ResourceRepository<User, String> {

    @Override
    public <S extends User> S save(S entity) {
        return entity;
    }

    @Override
    public void delete(String id) {

    }

    @Override
    public User findOne(String function, RequestParams requestParams) {
        return (new UserApiCall(environment, function, requestParams)).process();
    }

    @Override
    public Iterable<User> findAll(RequestParams requestParams) {
        return null;
    }

    @Override
    public Iterable<User> findAll(Iterable<String> ids, RequestParams requestParams) {
        // TODO Auto-generated method stub
        return null;
    }
}
