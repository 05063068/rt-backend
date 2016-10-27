package com.rottentomatoes.movieapi.domain.repository;

import com.rottentomatoes.movieapi.domain.model.Quote;

import io.katharsis.queryParams.RequestParams;
import io.katharsis.repository.ResourceRepository;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class QuoteRepository extends AbstractRepository implements ResourceRepository<Quote, String> {

    @Override
    public <S extends Quote> S save(S entity) {
        return null;
    }

    @Override
    public void delete(String id) {

    }

    @Override
    public Quote findOne(String id, RequestParams requestParams) {

        Map<String, Object> selectParams = new HashMap<>();

        EmsClient emsClient = emsConfig.fetchEmsClientForEndpoint("quote");
        Quote quote = (Quote) emsClient.callEmsEntity(selectParams, "quote", id, Quote.class);
        return quote;
    }

    @Override
    public Iterable<Quote> findAll(RequestParams requestParams) {
        return null;
    }

    @Override
    public Iterable<Quote> findAll(Iterable<String> ids, RequestParams requestParams) {
        return null;
    }

}
