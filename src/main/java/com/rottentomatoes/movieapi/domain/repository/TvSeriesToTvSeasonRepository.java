package com.rottentomatoes.movieapi.domain.repository;

import com.fasterxml.jackson.databind.type.TypeFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.rottentomatoes.movieapi.domain.model.TvSeason;
import com.rottentomatoes.movieapi.domain.model.TvSeries;

import io.katharsis.queryParams.RequestParams;
import io.katharsis.repository.RelationshipRepository;

@Component
public class TvSeriesToTvSeasonRepository extends AbstractRepository implements RelationshipRepository<TvSeries, String, TvSeason, String> {

    @Override
    public void addRelations(TvSeries arg0, Iterable<String> arg1, String arg2) {
    }

    @Override
    public void removeRelations(TvSeries arg0, Iterable<String> arg1, String arg2) {
    }

    @Override
    public void setRelation(TvSeries arg0, String TvSeason, String arg2) {
    }

    @Override
    public void setRelations(TvSeries arg0, Iterable<String> arg1, String arg2) {
    }

    @Override
    public TvSeason findOneTarget(String id, String fieldName, RequestParams requestParams) {
        return null;
    }

    @Override
    public Iterable<TvSeason> findManyTargets(String tvSeriesId, String fieldName, RequestParams requestParams) {
        Map<String, Object> selectParams = new HashMap<>();
        selectParams.put("limit", getLimit(fieldName, requestParams));

        PreEmsClient preEmsClient = new PreEmsClient<List<TvSeason>>(preEmsConfig);
        List<TvSeason> tvSeasonList = (List<TvSeason>)preEmsClient.callPreEmsList(selectParams, "tv-series", tvSeriesId + "/season", TypeFactory.defaultInstance().constructCollectionType(List.class,  TvSeason.class));

        return tvSeasonList;
    }
}