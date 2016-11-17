package com.rottentomatoes.movieapi.domain.repository;

import com.fasterxml.jackson.databind.type.TypeFactory;
import com.rottentomatoes.movieapi.domain.meta.RelatedMetaDataInformation;
import com.rottentomatoes.movieapi.domain.model.TvEpisode;
import com.rottentomatoes.movieapi.domain.model.TvSeason;
import com.rottentomatoes.movieapi.domain.model.VideoClip;
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

@SuppressWarnings("rawtypes")
@Component
public class TvSeasonToVideoClipRepository extends AbstractRepository implements RelationshipRepository<TvSeason, String, VideoClip, String>, MetaRepository {

    @Override
    public void setRelation(TvSeason tvSeason, String s, String s2) {

    }

    @Override
    public void setRelations(TvSeason tvSeason, Iterable<String> iterable, String s) {

    }

    @Override
    public void addRelations(TvSeason tvSeason, Iterable<String> iterable, String s) {

    }

    @Override
    public void removeRelations(TvSeason tvSeason, Iterable<String> iterable, String s) {

    }

    @Override
    public VideoClip findOneTarget(String s, String s2, RequestParams requestParams) {
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public MetaDataEnabledList<VideoClip> findManyTargets(String tvSeasonId, String fieldName, RequestParams requestParams) {
        Map<String, Object> selectParams = new HashMap<>();
        selectParams.put("limit", getLimit(fieldName, requestParams));
        selectParams.put("offset", getOffset(fieldName, requestParams));

        EmsClient emsClient = emsRouter.fetchEmsClientForEndpoint(this.getClass());
        List<VideoClip> rawVideoClipList = (List<VideoClip>)emsClient.callEmsList(selectParams, "tv/season", tvSeasonId + "/videos", TypeFactory.defaultInstance().constructCollectionType(List.class,  VideoClip.class));
        return new MetaDataEnabledList(rawVideoClipList);
    }

    @Override
    public MetaInformation getMetaInformation(Object root, Iterable resources, RequestParams requestParams, Serializable castedResourceId) {
        Map<String, Object> selectParams = new HashMap<>();

        EmsClient emsClient = emsRouter.fetchEmsClientForEndpoint(this.getClass());
        RelatedMetaDataInformation metaData = (RelatedMetaDataInformation) emsClient.callEmsEntity(selectParams, "tv/season", castedResourceId + "/videos/meta", RelatedMetaDataInformation.class);
        if (metaData != null && root instanceof RelationshipRepository) {
            metaData.setRequestParams(requestParams);
        }

        return metaData;
    }
}
