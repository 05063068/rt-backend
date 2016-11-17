package com.rottentomatoes.movieapi.domain.repository.tvseries;

import com.rottentomatoes.movieapi.domain.model.Image;
import com.rottentomatoes.movieapi.domain.model.TvSeries;

import com.rottentomatoes.movieapi.domain.repository.AbstractRepository;
import com.rottentomatoes.movieapi.domain.ems.EmsClient;
import io.katharsis.queryParams.RequestParams;
import io.katharsis.repository.MetaRepository;
import io.katharsis.repository.RelationshipRepository;
import io.katharsis.response.MetaInformation;

import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by patrick on 9/22/16.
 */
@SuppressWarnings("rawtypes")
@Component
public class TvSeriesToImageRepository extends AbstractRepository implements RelationshipRepository<TvSeries, String, Image, String>, MetaRepository {
    @Override
    public MetaInformation getMetaInformation(Object root, Iterable resources, RequestParams requestParams, Serializable castedResourceId) {
        return null;
    }

    @Override
    public void setRelation(TvSeries source, String targetId, String fieldName) {

    }

    @Override
    public void setRelations(TvSeries source, Iterable<String> targetIds, String fieldName) {

    }

    @Override
    public void addRelations(TvSeries source, Iterable<String> targetIds, String fieldName) {

    }

    @Override
    public void removeRelations(TvSeries source, Iterable<String> targetIds, String fieldName) {

    }

    @Override
    public Image findOneTarget(String tvSeriesId, String fieldName, RequestParams requestParams) {
        Map<String, Object> selectParams = new HashMap<>();
        EmsClient emsClient = emsRouter.fetchEmsClientForEndpoint(this.getClass());
        Image tvImage = (Image)emsClient.callEmsEntity(selectParams, "tv/series", tvSeriesId + "/main-image", Image.class);
        return tvImage;
    }

    @Override
    public Iterable<Image> findManyTargets(String sourceId, String fieldName, RequestParams requestParams) {
        return null;
    }
}
