package com.rottentomatoes.movieapi.domain.repository;

import com.rottentomatoes.movieapi.domain.meta.RootMetaDataInformation;
import com.rottentomatoes.movieapi.domain.model.Affiliate;
import com.rottentomatoes.movieapi.domain.model.Movie;

import io.katharsis.queryParams.RequestParams;
import io.katharsis.repository.MetaRepository;
import io.katharsis.repository.RelationshipRepository;
import io.katharsis.response.MetaInformation;

import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class MovieToAffiliatesRepository extends AbstractRepository implements RelationshipRepository<Movie, String, Affiliate, String>, MetaRepository {

    @Override
    public void setRelation(Movie source, String targetId, String fieldName) {

    }

    @Override
    public void setRelations(Movie source, Iterable<String> targetIds, String fieldName) {

    }

    @Override
    public void addRelations(Movie source, Iterable<String> targetIds, String fieldName) {

    }

    @Override
    public void removeRelations(Movie source, Iterable<String> targetIds, String fieldName) {

    }

    @Override
    public Affiliate findOneTarget(String affiliateId, String fieldName, RequestParams requestParams) {
        String movieId = affiliateId.substring(0, affiliateId.length() - 3);

        Map<String, Object> selectParams = new HashMap<>();
        selectParams.put("movie_id", movieId);

        Affiliate affiliate = sqlSession.selectOne("com.rottentomatoes.movieapi.mappers.AffiliateMapper.selectAffiliateById", selectParams);

        return affiliate;
    }

    @Override
    public Iterable<Affiliate> findManyTargets(String movieId, String fieldName, RequestParams requestParams) {
        Map<String, Object> selectParams = new HashMap<>();
        selectParams.put("movie_id", movieId);

        List<Affiliate> all_skus = sqlSession.selectList("com.rottentomatoes.movieapi.mappers.AffiliateMapper.selectAffiliateForMovie", selectParams);
        List<Affiliate> affiliates = new ArrayList<>();
        List<String> affiliateNames = new ArrayList<>();
        // filter one entry per affiliate, a group by in the query would jumble up the returned columns
        for(Affiliate a: all_skus) {
            String aName = a.getAffiliateName();
            if (!affiliateNames.contains(aName)) {
                affiliateNames.add(aName);
                affiliates.add(a);
            }
        }

        return affiliates;
    }
    
    @Override
    public MetaInformation getMetaInformation(Object root, Iterable resources, RequestParams requestParams, Serializable castedResourceId) {
        RootMetaDataInformation metaData = null;
        String movieId = castedResourceId.toString();
        Map<String, Object> selectParams = new HashMap<>();
        selectParams.put("movie_id", movieId);
        metaData = sqlSession.selectOne("com.rottentomatoes.movieapi.mappers.AffiliateMapper.selectAffiliatesForMovieCount", selectParams);
        metaData.setRequestParams(requestParams);
        return metaData;
    }
}
