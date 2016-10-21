package com.rottentomatoes.movieapi.domain.repository;

import com.fasterxml.jackson.databind.type.TypeFactory;
import com.rottentomatoes.movieapi.domain.model.Quote;
import com.rottentomatoes.movieapi.domain.model.Movie;
import io.katharsis.queryParams.RequestParams;
import io.katharsis.repository.RelationshipRepository;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class MovieToQuoteRepository extends AbstractRepository implements RelationshipRepository<Movie, String, Quote, String> {

    @Override
    public void addRelations(Movie arg0, Iterable<String> arg1, String arg2) {
    }

    @Override
    public void removeRelations(Movie arg0, Iterable<String> arg1, String arg2) {
    }

    @Override
    public void setRelation(Movie arg0, String arg1, String arg2) {
    }

    @Override
    public void setRelations(Movie arg0, Iterable<String> arg1, String arg2) {
    }

    @Override
    public Quote findOneTarget(String movieId, String fieldName, RequestParams requestParams) {
        return null;
    }

    @Override
    public Iterable<Quote> findManyTargets(String movieId, String fieldName, RequestParams requestParams) {
        Map<String, Object> selectParams = new HashMap<>();
        selectParams.put("limit", getLimit(fieldName, requestParams));
        selectParams.put("offset", getOffset(fieldName, requestParams));

        EmsClient emsClient = emsConfig.fetchEmsClientForEndpoint("movie");
        Iterable<Quote> quotes = (Iterable<Quote>) emsClient.callEmsList(selectParams, "movie", movieId + "/quote", TypeFactory.defaultInstance().constructCollectionType(List.class,  Quote.class));

        return quotes;
    }
}
