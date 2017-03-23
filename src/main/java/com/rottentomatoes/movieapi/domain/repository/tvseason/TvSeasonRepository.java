package com.rottentomatoes.movieapi.domain.repository.tvseason;

import com.fasterxml.jackson.databind.type.TypeFactory;
import com.rottentomatoes.movieapi.domain.model.TvSeason;
import com.rottentomatoes.movieapi.domain.repository.AbstractRepository;
import com.rottentomatoes.movieapi.domain.clients.ems.EmsClient;
import io.katharsis.queryParams.RequestParams;
import io.katharsis.repository.MetaRepository;
import io.katharsis.repository.ResourceRepository;
import io.katharsis.response.MetaInformation;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("rawtypes")
@Component
public class TvSeasonRepository extends AbstractRepository implements ResourceRepository<TvSeason, String>, MetaRepository {

    @Override
    public void delete(String s) {}

    @Override
    public <S extends TvSeason> S save(S s) {return null;}

    @Override
    public MetaInformation getMetaInformation(Object root, Iterable resources, Serializable castedResourceId, String fieldName, RequestParams requestParams) {
        return null;
    }

    @Override
    public TvSeason findOne(String tvSeasonId, RequestParams requestParams) {
        Map<String, Object> selectParams = new HashMap<>();
        EmsClient emsClient = emsRouter.fetchEmsClientForEndpoint(this.getClass());
        List<TvSeason> seasons = (List<TvSeason>)  emsClient.callEmsList(selectParams, "tv/season", tvSeasonId,
                TypeFactory.defaultInstance().constructCollectionType(List.class, TvSeason.class));

        // Necessary because endpoint returns a list of 1 element
        if (seasons != null && seasons.size() > 0) {
            return seasons.get(0);
        }
        return null;
    }

    @Override
    public Iterable<TvSeason> findAll(RequestParams requestParams) {
        return null;
    }

    @Override
    public Iterable<TvSeason> findAll(Iterable<String> iterable, RequestParams requestParams) {
        return null;
    }

}
