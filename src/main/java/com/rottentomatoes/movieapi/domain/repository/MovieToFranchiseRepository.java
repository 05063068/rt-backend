package com.rottentomatoes.movieapi.domain.repository;

import java.util.HashMap;
import java.util.Map;

import com.rottentomatoes.movieapi.domain.model.Franchise;
import org.springframework.stereotype.Component;

import com.rottentomatoes.movieapi.domain.model.Movie;

import io.katharsis.queryParams.RequestParams;
import io.katharsis.repository.RelationshipRepository;

@Component
public class MovieToFranchiseRepository extends AbstractRepository implements RelationshipRepository<Movie, String, Franchise, String> {

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
    public Franchise findOneTarget(String movieId, String fieldName, RequestParams requestParams) {
        Map<String, Object> selectParams = new HashMap<>();

        EmsClient emsClient = emsConfig.fetchEmsClientForEndpoint("movie");
        Franchise franchise = (Franchise) emsClient.callEmsEntity(selectParams, "movie", movieId + "/franchise", Franchise.class);
        if (franchise != null && franchise.getId() == null) {
            franchise = null;
        }
        return franchise;
    }

    @Override
    public Iterable<Franchise> findManyTargets(String movieId, String fieldName, RequestParams requestParams) {
        return null;
    }
}
