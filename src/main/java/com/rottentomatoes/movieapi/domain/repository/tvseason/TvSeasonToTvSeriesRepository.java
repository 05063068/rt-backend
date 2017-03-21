package com.rottentomatoes.movieapi.domain.repository.tvseason;

import com.fasterxml.jackson.databind.type.TypeFactory;
import com.rottentomatoes.movieapi.domain.model.TvSeason;
import com.rottentomatoes.movieapi.domain.model.TvSeries;
import com.rottentomatoes.movieapi.domain.repository.AbstractRepository;
import com.rottentomatoes.movieapi.domain.clients.ems.EmsClient;
import io.katharsis.queryParams.RequestParams;
import io.katharsis.repository.RelationshipRepository;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
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
        EmsClient emsClient = emsRouter.fetchEmsClientForEndpoint(this.getClass());
        List<TvSeries> seriesList = (List<TvSeries>) emsClient.callEmsIdList(selectParams, "tv/season", tvSeasonId + "/series", "tv/series",
                TypeFactory.defaultInstance().constructCollectionType(List.class, TvSeries.class));

        // Necessary because endpoint returns a list of 1 element
        if (seriesList != null && seriesList.size() > 0) {
            return seriesList.get(0);
        }
        return null;
    }

    @Override
    public Iterable<TvSeries> findManyTargets(String sourceId, String fieldName, RequestParams requestParams) {
        return null;
    }
}
