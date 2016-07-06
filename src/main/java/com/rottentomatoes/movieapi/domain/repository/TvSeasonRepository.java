package com.rottentomatoes.movieapi.domain.repository;

import com.rottentomatoes.movieapi.domain.model.TvSeason;
import io.katharsis.queryParams.RequestParams;
import io.katharsis.repository.MetaRepository;
import io.katharsis.repository.ResourceRepository;
import io.katharsis.response.MetaInformation;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.HashMap;
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
        selectParams.put("id", tvSeasonId);
        TvSeason tvSeason = sqlSession.selectOne("com.rottentomatoes.movieapi.mappers.TvSeasonMapper.selectTvSeasonById", selectParams);
        return tvSeason;
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
