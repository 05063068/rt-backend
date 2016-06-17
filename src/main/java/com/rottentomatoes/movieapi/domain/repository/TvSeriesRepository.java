package com.rottentomatoes.movieapi.domain.repository;

import com.rottentomatoes.movieapi.domain.model.TvSeries;
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
public class TvSeriesRepository extends AbstractRepository implements ResourceRepository<TvSeries, String>, MetaRepository {

    @Override
    public void delete(String s) {}

    @Override
    public <S extends TvSeries> S save(S s) {return null;}

    @Override
    public MetaInformation getMetaInformation(Object o, Iterable iterable, RequestParams requestParams, Serializable serializable) {
        return null;
    }

    @Override
    public TvSeries findOne(String tvSeriesId, RequestParams requestParams) {
        Map<String, Object> selectParams = new HashMap<>();
        selectParams.put("id", tvSeriesId);
        TvSeries tvSeries = sqlSession.selectOne("com.rottentomatoes.movieapi.mappers.TvSeriesMapper.selectTvSeriesById", selectParams);
        return tvSeries;
    }

    @Override
    public Iterable<TvSeries> findAll(RequestParams requestParams) {
        return null;
    }

    @Override
    public Iterable<TvSeries> findAll(Iterable<String> iterable, RequestParams requestParams) {
        return null;
    }

}
