package com.rottentomatoes.movieapi.domain.repository.tvseries;

import com.fasterxml.jackson.databind.type.TypeFactory;
import com.rottentomatoes.movieapi.domain.meta.RelatedMetaDataInformation;
import com.rottentomatoes.movieapi.domain.model.Image;
import com.rottentomatoes.movieapi.domain.model.TvSeries;

import com.rottentomatoes.movieapi.domain.repository.AbstractRepository;
import com.rottentomatoes.movieapi.domain.ems.EmsClient;
import com.rottentomatoes.movieapi.utils.RepositoryUtils;
import io.katharsis.queryParams.RequestParams;
import io.katharsis.repository.MetaRepository;
import io.katharsis.repository.RelationshipRepository;
import io.katharsis.response.MetaDataEnabledList;
import io.katharsis.response.MetaInformation;

import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by patrick on 9/22/16.
 */
@SuppressWarnings("rawtypes")
@Component
public class TvSeriesToImageRepository extends AbstractRepository implements RelationshipRepository<TvSeries, String, Image, String>, MetaRepository {

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
    public Iterable<Image> findManyTargets(String tvSeriesId, String fieldName, RequestParams requestParams) {
        Map<String, Object> selectParams = new HashMap<>();
        selectParams.put("limit", RepositoryUtils.getLimit(fieldName, requestParams));
        selectParams.put("offset", RepositoryUtils.getOffset(fieldName, requestParams));
        EmsClient emsClient = emsRouter.fetchEmsClientForEndpoint(this.getClass());
        List<Image> rawImageList = (List<Image>) emsClient.callEmsList(selectParams, "tv/series", tvSeriesId + "/images", TypeFactory.defaultInstance().constructCollectionType(List.class,  Image.class));
        return new MetaDataEnabledList(rawImageList);
    }

    @Override
    public MetaInformation getMetaInformation(Object root, Iterable resources, RequestParams requestParams, Serializable castedResourceId) {
        Map<String, Object> selectParams = new HashMap<>();
        // arbitrarily high limit
        selectParams.put("limit", 10000);

        EmsClient emsClient = emsRouter.fetchEmsClientForEndpoint(this.getClass());

//        RelatedMetaDataInformation metaData = (RelatedMetaDataInformation) emsClient.callEmsEntity(selectParams, "tv/series", castedResourceId + "/images/meta", RelatedMetaDataInformation.class);
//        if (metaData != null && root instanceof RelationshipRepository) {
//            metaData.setRequestParams(requestParams);
//        }

        List<Image> rawImageList = (List<Image>)emsClient.callEmsList(selectParams, "tv/series", castedResourceId + "/images", TypeFactory.defaultInstance().constructCollectionType(List.class,  Image.class));
        RelatedMetaDataInformation metaData = null;
        if (rawImageList != null) {
            metaData = new RelatedMetaDataInformation();
            metaData.setTotalCount(rawImageList.size());
            if (root instanceof RelationshipRepository) {
                metaData.setRequestParams(requestParams);
            }
        }

        return metaData;
    }
}
