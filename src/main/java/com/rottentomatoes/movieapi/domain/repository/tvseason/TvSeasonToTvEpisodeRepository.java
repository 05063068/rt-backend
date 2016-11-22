
package com.rottentomatoes.movieapi.domain.repository.tvseason;

import com.fasterxml.jackson.databind.type.TypeFactory;
import com.rottentomatoes.movieapi.domain.repository.AbstractRepository;
import com.rottentomatoes.movieapi.domain.ems.EmsClient;
import io.katharsis.queryParams.RequestParams;
import io.katharsis.repository.RelationshipRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.rottentomatoes.movieapi.domain.model.TvEpisode;
import com.rottentomatoes.movieapi.domain.model.TvSeason;

@SuppressWarnings("rawtypes")
@Component
public class TvSeasonToTvEpisodeRepository extends AbstractRepository implements
        RelationshipRepository<TvSeason, String, TvEpisode, String> {

    @Override
    public void addRelations(TvSeason arg0, Iterable<String> arg1, String arg2) {}

    @Override
    public void removeRelations(TvSeason arg0, Iterable<String> arg1, String arg2) {}

    @Override
    public void setRelation(TvSeason arg0, String arg1, String arg2) {}

    @Override
    public void setRelations(TvSeason arg0, Iterable<String> arg1, String arg2) {}

    @Override
    public TvEpisode findOneTarget(String arg0, String arg1, RequestParams arg2) {
        return null;
    }

    @Override
    public Iterable<TvEpisode> findManyTargets(String tvSeasonId, String fieldName,
            RequestParams requestParams) {
        Map<String, Object> selectParams = new HashMap<String, Object>();
        selectParams.put("limit", getLimit(fieldName, requestParams));

        EmsClient emsClient = emsRouter.fetchEmsClientForEndpoint(this.getClass());
        List<TvEpisode> tvEpisodeList = (List<TvEpisode>) emsClient.callEmsIdList(selectParams, "tv/season", tvSeasonId + "/episode", "tv/episode",
                TypeFactory.defaultInstance().constructCollectionType(List.class,  TvEpisode.class));
        return tvEpisodeList;
    }
}
