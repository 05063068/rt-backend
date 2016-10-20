package com.rottentomatoes.movieapi.domain.repository;

import com.fasterxml.jackson.databind.type.TypeFactory;
import com.rottentomatoes.movieapi.domain.model.TvSeason;
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
    public MetaInformation getMetaInformation(Object o, Iterable iterable, RequestParams requestParams, Serializable serializable) {
        return null;
    }

    @Override
    public TvSeason findOne(String tvSeasonId, RequestParams requestParams) {
        Map<String, Object> selectParams = new HashMap<>();
        EmsClient emsClient = emsConfig.fetchEmsClient("tv/season");
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
