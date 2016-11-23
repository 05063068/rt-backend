package com.rottentomatoes.movieapi.domain.repository.franchise;

import com.fasterxml.jackson.databind.type.TypeFactory;
import com.rottentomatoes.movieapi.domain.model.Franchise;
import com.rottentomatoes.movieapi.domain.model.Movie;
import com.rottentomatoes.movieapi.domain.repository.AbstractRepository;
import com.rottentomatoes.movieapi.domain.ems.EmsClient;
import com.rottentomatoes.movieapi.utils.RepositoryUtils;
import io.katharsis.queryParams.RequestParams;
import io.katharsis.repository.RelationshipRepository;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class FranchiseToMovieRepository extends AbstractRepository implements RelationshipRepository<Franchise, String, Movie, String> {

    @Override
    public void addRelations(Franchise arg0, Iterable<String> arg1, String arg2) {
    }

    @Override
    public void removeRelations(Franchise arg0, Iterable<String> arg1, String arg2) {
    }

    @Override
    public void setRelation(Franchise arg0, String Movie, String arg2) {
    }

    @Override
    public void setRelations(Franchise arg0, Iterable<String> arg1, String arg2) {
    }

    @Override
    public Movie findOneTarget(String id, String fieldName, RequestParams requestParams) {
        return null;
    }

    @Override
    public Iterable<Movie> findManyTargets(String franchiseId, String fieldName, RequestParams requestParams) {
        Map<String, Object> selectParams = new HashMap<>();
        selectParams.put("limit", RepositoryUtils.getLimit(fieldName, requestParams));
        selectParams.put("offset", RepositoryUtils.getOffset(fieldName, requestParams));

        EmsClient emsClient = emsRouter.fetchEmsClientForEndpoint(this.getClass());
        List<String> movieIds = (List<String>) emsClient.callEmsList(selectParams, "franchise", franchiseId + "/movie",
                TypeFactory.defaultInstance().constructCollectionType(List.class,  String.class));

        emsClient = emsRouter.fetchEmsClientForEndpoint(this.getClass());
        if (movieIds != null && movieIds.size() > 0) {
            String ids = String.join(",", movieIds);
            List<Movie> movieList = (List<Movie>) emsClient.callEmsList(selectParams, "movie", ids,
                    TypeFactory.defaultInstance().constructCollectionType(List.class, Movie.class));
            return movieList;
        }
        return null;
    }
}
