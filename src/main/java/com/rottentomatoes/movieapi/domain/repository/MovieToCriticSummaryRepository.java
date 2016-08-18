package com.rottentomatoes.movieapi.domain.repository;

import com.rottentomatoes.movieapi.domain.model.CriticSummary;
import com.rottentomatoes.movieapi.domain.model.Movie;
import io.katharsis.queryParams.RequestParams;
import io.katharsis.repository.RelationshipRepository;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class MovieToCriticSummaryRepository extends AbstractRepository implements RelationshipRepository<Movie, String, CriticSummary, String> {

    private static final String CRITIC_TYPE = "criticType";
    private static final String TOP_CRITICS = "top";

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
    public CriticSummary findOneTarget(String movieId, String fieldName, RequestParams requestParams) {
        Map<String, Object> selectParams = new HashMap<>();
        selectParams.put("country", getCountry(requestParams).getCountryCode());

        if (requestParams.getFilters() != null && requestParams.getFilters().containsKey(CRITIC_TYPE) && ((String) requestParams.getFilters().get(CRITIC_TYPE)).equalsIgnoreCase(TOP_CRITICS)) {
            selectParams.put(CRITIC_TYPE, TOP_CRITICS);
        }

        PreEmsClient preEmsClient = new PreEmsClient(preEmsConfig);
        CriticSummary criticSummary = (CriticSummary) preEmsClient.callPreEmsEntity(selectParams, "movie", movieId + "/critic-summary", CriticSummary.class);
        return criticSummary;
    }

    @Override
    public Iterable<CriticSummary> findManyTargets(String s, String s2, RequestParams requestParams) {
        return null;
    }
}
