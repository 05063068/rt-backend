package com.rottentomatoes.movieapi.domain.repository;

import com.rottentomatoes.movieapi.domain.model.TvSeason;
import com.rottentomatoes.movieapi.domain.model.TvSeries;
import io.katharsis.queryParams.RequestParams;
import io.katharsis.repository.RelationshipRepository;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class TvSeasonToTvSeriesRepository extends AbstractRepository implements RelationshipRepository<TvSeason, String, TvSeries, String> {


    @Override
    public void setRelation(TvSeason source, String targetId, String fieldName) {

    }

    @Override
    public void setRelations(TvSeason source, Iterable<String> targetIds, String fieldName) {

    }

    @Override
    public void addRelations(TvSeason source, Iterable<String> targetIds, String fieldName) {

    }

    @Override
    public void removeRelations(TvSeason source, Iterable<String> targetIds, String fieldName) {

    }

    @Override
    public TvSeries findOneTarget(String tvSeasonId, String fieldName, RequestParams requestParams) {
        Map<String, Object> selectParams = new HashMap<>();
        PreEmsClient preEmsClient = new PreEmsClient(preEmsConfig);
        TvSeries tvSeries = (TvSeries) preEmsClient.callPreEmsEntity(selectParams, "tv-season", tvSeasonId + "/series", TvSeries.class);
        return tvSeries;
    }

    @Override
    public Iterable<TvSeries> findManyTargets(String sourceId, String fieldName, RequestParams requestParams) {
        return null;
    }
}
