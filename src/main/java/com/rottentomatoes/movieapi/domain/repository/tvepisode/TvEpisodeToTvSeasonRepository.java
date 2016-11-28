package com.rottentomatoes.movieapi.domain.repository.tvepisode;

import com.fasterxml.jackson.databind.type.TypeFactory;
import com.rottentomatoes.movieapi.domain.ems.EmsClient;
import com.rottentomatoes.movieapi.domain.model.TvEpisode;
import com.rottentomatoes.movieapi.domain.model.TvSeason;
import com.rottentomatoes.movieapi.domain.model.TvSeries;
import com.rottentomatoes.movieapi.domain.repository.AbstractRepository;
import io.katharsis.queryParams.RequestParams;
import io.katharsis.repository.RelationshipRepository;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class TvEpisodeToTvSeasonRepository extends AbstractRepository implements RelationshipRepository<TvEpisode, String, TvSeason, String> {


    @Override
    public void setRelation(TvEpisode source, String targetId, String fieldName) {

    }

    @Override
    public void setRelations(TvEpisode source, Iterable<String> targetIds, String fieldName) {

    }

    @Override
    public void addRelations(TvEpisode source, Iterable<String> targetIds, String fieldName) {

    }

    @Override
    public void removeRelations(TvEpisode source, Iterable<String> targetIds, String fieldName) {

    }

    @Override
    public TvSeason findOneTarget(String tvEpisodeId, String fieldName, RequestParams requestParams) {
        Map<String, Object> selectParams = new HashMap<>();
        EmsClient emsClient = emsRouter.fetchEmsClientForEndpoint(this.getClass());
        List<TvSeason> seasonList = (List<TvSeason>) emsClient.callEmsIdList(selectParams, "tv/episode", tvEpisodeId + "/season", "tv/",
                TypeFactory.defaultInstance().constructCollectionType(List.class, TvSeason.class));

        // Necessary because endpoint returns a list of 1 element
        if (seasonList != null && seasonList.size() > 0) {
            return seasonList.get(0);
        }
        return null;
    }

    @Override
    public Iterable<TvSeason> findManyTargets(String sourceId, String fieldName, RequestParams requestParams) {
        return null;
    }
}
