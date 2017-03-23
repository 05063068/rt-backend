package com.rottentomatoes.movieapi.domain.repository.tvepisode;

import com.fasterxml.jackson.databind.type.TypeFactory;
import com.rottentomatoes.movieapi.domain.clients.ems.EmsClient;
import com.rottentomatoes.movieapi.domain.model.meta.RelatedMetaDataInformation;
import com.rottentomatoes.movieapi.domain.model.Image;
import com.rottentomatoes.movieapi.domain.model.TvEpisode;
import com.rottentomatoes.movieapi.domain.repository.AbstractRepository;
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

@SuppressWarnings("rawtypes")
@Component
public class TvEpisodeToImageRepository extends AbstractRepository implements RelationshipRepository<TvEpisode, String, Image, String>, MetaRepository {

    @Override
    public void addRelations(TvEpisode arg0, Iterable<String> arg1, String arg2) {
    }

    @Override
    public void removeRelations(TvEpisode arg0, Iterable<String> arg1, String arg2) {
    }

    @Override
    public void setRelation(TvEpisode arg0, String arg1, String arg2) {
    }

    @Override
    public void setRelations(TvEpisode arg0, Iterable<String> arg1, String arg2) {
    }

    @Override
    public Image findOneTarget(String id, String fieldName, RequestParams requestParams) {
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public MetaDataEnabledList<Image> findManyTargets(String tvEpisodeId, String fieldName, RequestParams requestParams) {
        Map<String, Object> selectParams = new HashMap<>();
        selectParams.put("limit", RepositoryUtils.getLimit(fieldName, requestParams));
        selectParams.put("offset", RepositoryUtils.getOffset(fieldName, requestParams));
        EmsClient emsClient = emsRouter.fetchEmsClientForEndpoint(this.getClass());
        List<Image> rawImageList = (List<Image>) emsClient.callEmsList(selectParams, "tv/episode", tvEpisodeId + "/images", TypeFactory.defaultInstance().constructCollectionType(List.class,  Image.class));
        return new MetaDataEnabledList(rawImageList);
    }

    @Override
    public MetaInformation getMetaInformation(Object root, Iterable resources, Serializable castedResourceId, String fieldName, RequestParams requestParams) {
        Map<String, Object> selectParams = new HashMap<>();
        // arbitrarily high limit
        selectParams.put("limit", 10000);

        EmsClient emsClient = emsRouter.fetchEmsClientForEndpoint(this.getClass());

//        RelatedMetaDataInformation metaData = (RelatedMetaDataInformation) emsClient.callEmsEntity(selectParams, "tv/episode", castedResourceId + "/images/meta", RelatedMetaDataInformation.class);
//        if (metaData != null && root instanceof RelationshipRepository) {
//            metaData.setRequestParams(requestParams);
//        }

        List<Image> rawImageList = (List<Image>)emsClient.callEmsList(selectParams, "tv/episode", castedResourceId + "/images", TypeFactory.defaultInstance().constructCollectionType(List.class,  Image.class));
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
