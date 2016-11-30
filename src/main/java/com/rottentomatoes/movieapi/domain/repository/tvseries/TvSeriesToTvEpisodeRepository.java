package com.rottentomatoes.movieapi.domain.repository.tvseries;

import com.fasterxml.jackson.databind.type.TypeFactory;
import com.rottentomatoes.movieapi.domain.ems.EmsClient;
import com.rottentomatoes.movieapi.domain.model.TvEpisode;
import com.rottentomatoes.movieapi.domain.model.TvSeason;
import com.rottentomatoes.movieapi.domain.model.TvSeries;
import com.rottentomatoes.movieapi.domain.repository.AbstractRepository;
import com.rottentomatoes.movieapi.utils.RepositoryUtils;
import io.katharsis.queryParams.RequestParams;
import io.katharsis.repository.RelationshipRepository;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class TvSeriesToTvEpisodeRepository extends AbstractRepository implements RelationshipRepository<TvSeries, String, TvEpisode, String> {

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
    public TvEpisode findOneTarget(String id, String fieldName, RequestParams requestParams) {Map<String, Object> selectParams = new HashMap<String, Object>();
        EmsClient emsClient = emsRouter.fetchEmsClientForEndpoint(this.getClass());
        Map tvEpisodes = (Map) emsClient.callEmsEntity(selectParams, "tv/series", id + "/episode-info", Map.class);

        if (tvEpisodes != null && tvEpisodes.containsKey(fieldName)) {
            String tvEpisodeId = Integer.toString((Integer) tvEpisodes.get(fieldName));
            if (tvEpisodeId != null) {
                List<TvEpisode> episodes = (List<TvEpisode>) emsClient.callEmsList(selectParams, "tv/episode", tvEpisodeId,
                        TypeFactory.defaultInstance().constructCollectionType(List.class, TvEpisode.class));

                // Necessary because endpoint returns a list of 1 element
                if (episodes != null && episodes.size() > 0) {
                    return episodes.get(0);
                }
            }
        }
        return null;
    }

    @Override
    public Iterable<TvEpisode> findManyTargets(String tvSeriesId, String fieldName, RequestParams requestParams) {
        return null;
    }
}
