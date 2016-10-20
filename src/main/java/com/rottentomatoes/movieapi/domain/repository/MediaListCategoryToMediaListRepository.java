package com.rottentomatoes.movieapi.domain.repository;

import com.rottentomatoes.movieapi.domain.model.MediaList;
import com.rottentomatoes.movieapi.domain.model.MediaListCategory;
import io.katharsis.queryParams.RequestParams;
import io.katharsis.repository.RelationshipRepository;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by patrick on 9/1/16.
 */
@SuppressWarnings("rawtypes")
@Component
public class MediaListCategoryToMediaListRepository extends AbstractRepository implements RelationshipRepository<MediaListCategory, String, MediaList, String> {

    @Override
    public void setRelation(MediaListCategory source, String targetId, String fieldName) {

    }

    @Override
    public void setRelations(MediaListCategory source, Iterable<String> targetIds, String fieldName) {

    }

    @Override
    public void addRelations(MediaListCategory source, Iterable<String> targetIds, String fieldName) {

    }

    @Override
    public void removeRelations(MediaListCategory source, Iterable<String> targetIds, String fieldName) {

    }

    @Override
    public MediaList findOneTarget(String sourceId, String fieldName, RequestParams requestParams) {
        Map<String, Object> selectParams = new HashMap<>();

        EmsClient emsClient = emsConfig.fetchEmsClient("media-list-category");
        MediaList activeMediaList = (MediaList)emsClient.callEmsEntity(selectParams, "media-list-category", sourceId + "/list", MediaList.class);

        return activeMediaList;
    }

    @Override
    public Iterable<MediaList> findManyTargets(String sourceId, String fieldName, RequestParams requestParams) {
       return null;
    }
}
