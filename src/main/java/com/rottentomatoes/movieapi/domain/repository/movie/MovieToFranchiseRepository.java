package com.rottentomatoes.movieapi.domain.repository.movie;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.type.TypeFactory;
import com.rottentomatoes.movieapi.domain.model.Franchise;
import com.rottentomatoes.movieapi.domain.repository.AbstractRepository;
import com.rottentomatoes.movieapi.domain.ems.EmsClient;
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

        EmsClient emsClient = emsRouter.fetchEmsClientForEndpoint(this.getClass());
        List<Franchise> list = (List<Franchise>) emsClient.callEmsIdList(selectParams, "movie", movieId + "/franchise", "franchise", TypeFactory.defaultInstance().constructCollectionType(List.class, Franchise.class));
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public Iterable<Franchise> findManyTargets(String movieId, String fieldName, RequestParams requestParams) {
        return null;
    }
}
