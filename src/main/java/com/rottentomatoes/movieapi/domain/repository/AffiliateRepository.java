package com.rottentomatoes.movieapi.domain.repository;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.rottentomatoes.movieapi.domain.meta.RootMetaDataInformation;
import com.rottentomatoes.movieapi.domain.model.Affiliate;

import com.rottentomatoes.movieapi.domain.repository.ems.EmsClient;
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

        EmsClient emsClient = emsRouter.fetchEmsClientForEndpoint(this.getClass());
        Affiliate affiliate = (Affiliate)emsClient.callEmsEntity(selectParams, "movie", movieId + "/affiliate/" + id, Affiliate.class);

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
        Map<String, Object> selectParams = new HashMap<>();
        String id = castedResourceId.toString();
        String movieId = id.substring(0, id.length() - 2);
        EmsClient emsClient = emsRouter.fetchEmsClientForEndpoint(this.getClass());
        RootMetaDataInformation metaData = (RootMetaDataInformation) emsClient.callEmsEntity(selectParams, "movie", movieId + "/affiliate/" + id + "/meta", RootMetaDataInformation.class);
        metaData.setRequestParams(requestParams);
        return metaData;
    }
}
