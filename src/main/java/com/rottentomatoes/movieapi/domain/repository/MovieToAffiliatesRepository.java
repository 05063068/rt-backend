package com.rottentomatoes.movieapi.domain.repository;

import com.fasterxml.jackson.databind.type.TypeFactory;
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
        String movieId = affiliateId.substring(0, affiliateId.length() - 2);

        Map<String, Object> selectParams = new HashMap<>();

        PreEmsClient preEmsClient = new PreEmsClient<Affiliate>(preEmsConfig);
        Affiliate affiliate = (Affiliate)preEmsClient.callPreEmsEntity(selectParams, "movie", movieId + "/affiliate/" + affiliateId, Affiliate.class);


        return affiliate;
    }

    @Override
    public Iterable<Affiliate> findManyTargets(String movieId, String fieldName, RequestParams requestParams) {
        Map<String, Object> selectParams = new HashMap<>();

        PreEmsClient preEmsClient = new PreEmsClient<List<Affiliate>>(preEmsConfig);
        List<Affiliate> all_skus = (List<Affiliate>)preEmsClient.callPreEmsList(selectParams, "movie", movieId + "/affiliate", TypeFactory.defaultInstance().constructCollectionType(List.class,  Affiliate.class));

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
        String id = "all";
        Map<String, Object> selectParams = new HashMap<>();
        PreEmsClient preEmsClient = new PreEmsClient<RootMetaDataInformation>(preEmsConfig);
        metaData = (RootMetaDataInformation) preEmsClient.callPreEmsEntity(selectParams, "movie", movieId + "/affiliate/" + id + "/meta", RootMetaDataInformation.class);
        metaData.setRequestParams(requestParams);
        return metaData;
    }
}
