
package com.rottentomatoes.movieapi.domain.repository.tvepisode;

import com.fasterxml.jackson.databind.type.TypeFactory;
import com.rottentomatoes.movieapi.domain.meta.RelatedMetaDataInformation;
import com.rottentomatoes.movieapi.domain.model.Image;
import com.rottentomatoes.movieapi.domain.repository.AbstractRepository;
import com.rottentomatoes.movieapi.domain.ems.EmsClient;
import io.katharsis.queryParams.RequestParams;
import io.katharsis.repository.MetaRepository;
import io.katharsis.repository.RelationshipRepository;
import io.katharsis.repository.ResourceRepository;
import io.katharsis.response.MetaDataEnabledList;
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
        EmsClient emsClient = emsRouter.fetchEmsClientForEndpoint(this.getClass());
        List<TvEpisode> episodes = (List<TvEpisode>) emsClient.callEmsList(selectParams, "tv/episode", tvEpisodeId,
                TypeFactory.defaultInstance().constructCollectionType(List.class, TvEpisode.class));

        // Necessary because endpoint returns a list of 1 element
        if (episodes != null && episodes.size() > 0) {
            return episodes.get(0);
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
    public MetaInformation getMetaInformation(Object root, Iterable resources, Serializable castedResourceId, String fieldName, RequestParams requestParams) {
        return null;
    }
}
