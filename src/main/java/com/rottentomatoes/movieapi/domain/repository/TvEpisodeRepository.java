
package com.rottentomatoes.movieapi.domain.repository;

import io.katharsis.queryParams.RequestParams;
import io.katharsis.repository.MetaRepository;
import io.katharsis.repository.ResourceRepository;
import io.katharsis.response.MetaInformation;

import java.io.Serializable;
import java.util.HashMap;
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
        PreEmsClient preEmsClient = new PreEmsClient(preEmsConfig);
        TvEpisode tvEpisode = (TvEpisode) preEmsClient.callPreEmsEntity(selectParams, "tv-episode", tvEpisodeId, TvEpisode.class);

        return tvEpisode;
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
