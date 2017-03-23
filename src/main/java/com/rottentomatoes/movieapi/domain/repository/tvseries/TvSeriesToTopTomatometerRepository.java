package com.rottentomatoes.movieapi.domain.repository.tvseries;

import com.fasterxml.jackson.databind.type.TypeFactory;
import com.rottentomatoes.movieapi.domain.model.TopTomatometer;
import com.rottentomatoes.movieapi.domain.model.TvSeries;
import com.rottentomatoes.movieapi.domain.repository.AbstractRepository;
import com.rottentomatoes.movieapi.domain.clients.ems.EmsClient;
import com.rottentomatoes.movieapi.utils.RepositoryUtils;
import io.katharsis.queryParams.RequestParams;
import io.katharsis.repository.RelationshipRepository;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class TvSeriesToTopTomatometerRepository extends AbstractRepository implements RelationshipRepository<TvSeries, String, TopTomatometer, String> {

    @Override
    public void setRelation(TvSeries tvSeries, String s, String s2) {

    }

    @Override
    public void setRelations(TvSeries tvSeries, Iterable<String> iterable, String s) {

    }

    @Override
    public void addRelations(TvSeries tvSeries, Iterable<String> iterable, String s) {

    }

    @Override
    public void removeRelations(TvSeries tvSeries, Iterable<String> iterable, String s) {

    }

    @Override
    public TopTomatometer findOneTarget(String tvSeriesId, String fieldName, RequestParams requestParams) {

        Map<String, Object> selectParams = new HashMap<>();
        selectParams.put("limit", RepositoryUtils.getLimit(fieldName, requestParams));
        selectParams.put("offset", RepositoryUtils.getOffset(fieldName, requestParams));
        selectParams.put("country", RepositoryUtils.getCountry(requestParams).getCountryCode());
        EmsClient emsClient = emsRouter.fetchEmsClientForEndpoint(this.getClass());
        List<TopTomatometer> topTomatometerList = (List<TopTomatometer>) emsClient.callEmsList(selectParams, "tv/series", tvSeriesId + "/top-tomatometer", TypeFactory.defaultInstance().constructCollectionType(List.class,  TopTomatometer.class));

        // Necessary because endpoint returns a list of 1 element
        if (topTomatometerList != null && topTomatometerList.size() > 0) {
            TopTomatometer topTomatometer = topTomatometerList.get(0);
            topTomatometer.setId("tt-" + tvSeriesId);
            return topTomatometer;
        }
        return null;
    }

    @Override
    public Iterable<TopTomatometer> findManyTargets(String tvSeasonId, String fieldName, RequestParams requestParams) {
        return null;
    }
}
