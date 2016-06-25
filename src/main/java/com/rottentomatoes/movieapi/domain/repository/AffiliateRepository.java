package com.rottentomatoes.movieapi.domain.repository;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.rottentomatoes.movieapi.domain.meta.RootMetaDataInformation;
import com.rottentomatoes.movieapi.domain.model.Affiliate;

import org.springframework.stereotype.Component;

import io.katharsis.queryParams.RequestParams;
import io.katharsis.repository.MetaRepository;
import io.katharsis.repository.ResourceRepository;
import io.katharsis.response.MetaInformation;

@Component
public class AffiliateRepository extends AbstractRepository implements ResourceRepository<Affiliate, String>, MetaRepository<Affiliate> {
    @Override
    public <S extends Affiliate> S save(S entity) {
        return null;
    }

    @Override
    public void delete(String id) {

    }

    @Override
    public Affiliate findOne(String id, RequestParams requestParams) {

        Map<String, Object> selectParams = new HashMap<>();
        selectParams.put("id", id);
        String movieId = id.substring(0, id.length() - 3);
        selectParams.put("movie_id", movieId);

        Affiliate genre = sqlSession.selectOne("com.rottentomatoes.movieapi.mappers.AffiliateMapper.selectAffiliateById", selectParams);
        return genre;
    }

    @Override
    public Iterable<Affiliate> findAll(RequestParams requestParams) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Iterable<Affiliate> findAll(Iterable<String> ids, RequestParams requestParams) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public MetaInformation getMetaInformation(Object root, Iterable resources, RequestParams requestParams, Serializable castedResourceId) {
        RootMetaDataInformation metaData = null;
        Map<String, Object> selectParams = new HashMap<>();
        metaData = sqlSession.selectOne("com.rottentomatoes.movieapi.mappers.AffiliateMapper.selectAffiliatesForMovieCount", selectParams);
        metaData.setRequestParams(requestParams);
        return metaData;
    }
}
