package com.rottentomatoes.movieapi.domain.repository;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.type.TypeFactory;
import com.rottentomatoes.movieapi.domain.meta.RootMetaDataInformation;
import com.rottentomatoes.movieapi.domain.model.Affiliate;
import com.rottentomatoes.movieapi.domain.ems.EmsClient;

import org.springframework.stereotype.Component;

import io.katharsis.queryParams.RequestParams;
import io.katharsis.repository.MetaRepository;
import io.katharsis.repository.ResourceRepository;
import io.katharsis.response.MetaInformation;

@Component
public class AffiliateRepository extends AbstractRepository implements ResourceRepository<Affiliate, String>, MetaRepository<Affiliate> {
    private EmsClient emsClient;

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
        String movieId = id.substring(0, id.length() - 2);
        String affiliateId = id.substring(id.length() -2);

        // re-fetch list of affiliates and pick the one requested to return
        EmsClient emsClient = emsRouter.fetchEmsClientForEndpoint(this.getClass());
        List<Affiliate> all_skus = (List<Affiliate>)emsClient.callEmsList(selectParams, emsClient.getPathBase("movie"), 
                emsClient.getIdString("", movieId, "/affiliate"), TypeFactory.defaultInstance().constructCollectionType(List.class,  Affiliate.class));
        Affiliate affiliate = null;
        for (Affiliate a: all_skus) {
            if (a.getId().substring(a.getId().length() -2).equals(affiliateId)) {
                affiliate = a;
                return affiliate;
            }
        }
        return affiliate;
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
         return null;
    }
}
