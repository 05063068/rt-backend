package com.rottentomatoes.movieapi.domain.repository.account;

import com.rottentomatoes.movieapi.domain.apicalldelegators.account.SessionApiCall;
import com.rottentomatoes.movieapi.domain.model.account.Session;
import com.rottentomatoes.movieapi.domain.repository.AbstractRepository;
import io.katharsis.queryParams.RequestParams;
import io.katharsis.repository.ResourceRepository;
import org.springframework.stereotype.Component;

@Component
public class SessionRepository extends AbstractRepository implements ResourceRepository<Session, String> {

    @Override
    public <S extends Session> S save(S entity) {
        return entity;
    }

    @Override
    public void delete(String id) {

    }

    @Override
    public Session findOne(String function, RequestParams requestParams) {
        return (new SessionApiCall(environment, function, requestParams)).process();
    }

    @Override
    public Iterable<Session> findAll(RequestParams requestParams) {
        return null;
    }

    @Override
    public Iterable<Session> findAll(Iterable<String> ids, RequestParams requestParams) {
        // TODO Auto-generated method stub
        return null;
    }
}
