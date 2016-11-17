package com.rottentomatoes.movieapi.domain.repository.movie;

import com.rottentomatoes.movieapi.domain.model.AudienceSummary;
import com.rottentomatoes.movieapi.domain.model.Movie;
import com.rottentomatoes.movieapi.domain.repository.AbstractRepository;
import com.rottentomatoes.movieapi.domain.repository.ems.EmsClient;
import io.katharsis.queryParams.RequestParams;
import io.katharsis.repository.RelationshipRepository;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class MovieToAudienceSummaryRepository extends AbstractRepository implements RelationshipRepository<Movie, String, AudienceSummary, String> {

    @Override
    public void setRelation(Movie movie, String s, String s2) {

    }

    @Override
    public void setRelations(Movie movie, Iterable<String> iterable, String s) {

    }

    @Override
    public void addRelations(Movie movie, Iterable<String> iterable, String s) {

    }

    @Override
    public void removeRelations(Movie movie, Iterable<String> iterable, String s) {

    }

    @Override
    public AudienceSummary findOneTarget(String movieId, String fieldName, RequestParams requestParams) {
        Map<String, Object> selectParams = new HashMap<>();

        EmsClient emsClient = emsRouter.fetchEmsClientForEndpoint(this.getClass());
        AudienceSummary audienceSummary = (AudienceSummary) emsClient.callEmsEntity(selectParams, "movie", movieId + "/audience-summary", AudienceSummary.class);
        return audienceSummary;
    }

    @Override
    public Iterable<AudienceSummary> findManyTargets(String s, String s2, RequestParams requestParams) {
        return null;
    }
}
