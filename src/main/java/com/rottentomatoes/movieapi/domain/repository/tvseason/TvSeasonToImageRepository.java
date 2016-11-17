package com.rottentomatoes.movieapi.domain.repository.tvseason;

import com.fasterxml.jackson.databind.type.TypeFactory;
import com.rottentomatoes.movieapi.domain.meta.RelatedMetaDataInformation;
import com.rottentomatoes.movieapi.domain.model.Image;
import com.rottentomatoes.movieapi.domain.model.TvSeason;
import com.rottentomatoes.movieapi.domain.repository.AbstractRepository;
import com.rottentomatoes.movieapi.domain.ems.EmsClient;
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
public class TvSeasonToImageRepository extends AbstractRepository implements RelationshipRepository<TvSeason, String, Image, String>, MetaRepository {

    @Override
    public void setRelation(TvSeason source, String targetId, String fieldName) {

    }

    @Override
    public void setRelations(TvSeason source, Iterable<String> targetIds, String fieldName) {

    }

    @Override
    public void addRelations(TvSeason source, Iterable<String> targetIds, String fieldName) {

    }

    @Override
    public void removeRelations(TvSeason source, Iterable<String> targetIds, String fieldName) {

    }

    @Override
    public Image findOneTarget(String tvSeasonId, String fieldName, RequestParams requestParams) {
        Map<String, Object> selectParams = new HashMap<>();
        EmsClient emsClient = emsRouter.fetchEmsClientForEndpoint(this.getClass());
        Image tvImage = (Image)emsClient.callEmsEntity(selectParams, "tv/season", tvSeasonId + "/main-image", Image.class);
        return tvImage;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Iterable<Image> findManyTargets(String tvSeasonId, String fieldName, RequestParams requestParams) {
        Map<String, Object> selectParams = new HashMap<>();
        selectParams.put("limit", getLimit(fieldName, requestParams));
        selectParams.put("offset", getOffset(fieldName, requestParams));
        EmsClient emsClient = emsRouter.fetchEmsClientForEndpoint(this.getClass());
        List<Image> rawImageList = (List<Image>) emsClient.callEmsList(selectParams, "tv/season", tvSeasonId + "/images", TypeFactory.defaultInstance().constructCollectionType(List.class,  Image.class));
        return new MetaDataEnabledList(rawImageList);
    }

    @Override
    public MetaInformation getMetaInformation(Object root, Iterable resources, RequestParams requestParams, Serializable castedResourceId) {
        Map<String, Object> selectParams = new HashMap<>();

        EmsClient emsClient = emsRouter.fetchEmsClientForEndpoint(this.getClass());
        RelatedMetaDataInformation metaData = (RelatedMetaDataInformation) emsClient.callEmsEntity(selectParams, "tv/season", castedResourceId + "/images/meta", RelatedMetaDataInformation.class);
        if (metaData != null && root instanceof RelationshipRepository) {
            metaData.setRequestParams(requestParams);
        }
        return metaData;
    }
}
