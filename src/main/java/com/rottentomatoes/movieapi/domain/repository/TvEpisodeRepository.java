
package com.rottentomatoes.movieapi.domain.repository;

import com.fasterxml.jackson.databind.type.TypeFactory;
import io.katharsis.queryParams.RequestParams;
import io.katharsis.repository.MetaRepository;
import io.katharsis.repository.ResourceRepository;
import io.katharsis.response.MetaInformation;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.rottentomatoes.movieapi.domain.model.TvEpisode;

@SuppressWarnings("rawtypes")
@Component
public class TvEpisodeRepository extends AbstractRepository implements
        ResourceRepository<TvEpisode, String>, MetaRepository {

    @Override
    public void delete(String s) {}

    @Override
    public <S extends TvEpisode> S save(S s) {
        return null;
    }

    @Override
    public TvEpisode findOne(String tvEpisodeId, RequestParams requestParams) {
        Map<String, Object> selectParams = new HashMap<>();
        EmsClient emsClient = new EmsClient<List<TvEpisode>>(emsConfig);

        // Retrieves a list of 1 object, due to how EMS is set up
        List<TvEpisode> tvEpisodes = (List<TvEpisode>) emsClient.callEmsList(selectParams, "tv/episode", tvEpisodeId,
                TypeFactory.defaultInstance().constructCollectionType(List.class, TvEpisode.class));

        if (tvEpisodes != null && tvEpisodes.size() > 0) {
            return tvEpisodes.get(0);
        }
        return null;
    }

    @Override
    public Iterable<TvEpisode> findAll(RequestParams requestParams) {
        return null;
    }

    @Override
    public Iterable<TvEpisode> findAll(Iterable<String> iterable, RequestParams requestParams) {
        return null;
    }

    @Override
    public MetaInformation getMetaInformation(Object o, Iterable iterable,
            RequestParams requestParams, Serializable serializable) {
        return null;
    }
}
