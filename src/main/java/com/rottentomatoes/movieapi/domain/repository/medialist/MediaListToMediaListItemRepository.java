package com.rottentomatoes.movieapi.domain.repository.medialist;

import com.fasterxml.jackson.databind.type.TypeFactory;
import com.rottentomatoes.movieapi.domain.model.MediaList;
import com.rottentomatoes.movieapi.domain.model.MediaListItem;

import com.rottentomatoes.movieapi.domain.repository.AbstractRepository;
import com.rottentomatoes.movieapi.domain.ems.EmsClient;
import io.katharsis.queryParams.RequestParams;
import io.katharsis.repository.RelationshipRepository;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by patrick on 9/1/16.
 */
@SuppressWarnings("rawtypes")
@Component
public class MediaListToMediaListItemRepository extends AbstractRepository implements RelationshipRepository<MediaList, String, MediaListItem, String> {

    @Override
    public void setRelation(MediaList source, String targetId, String fieldName) {

    }

    @Override
    public void setRelations(MediaList source, Iterable<String> targetIds, String fieldName) {

    }

    @Override
    public void addRelations(MediaList source, Iterable<String> targetIds, String fieldName) {

    }

    @Override
    public void removeRelations(MediaList source, Iterable<String> targetIds, String fieldName) {

    }

    @Override
    public MediaListItem findOneTarget(String sourceId, String fieldName, RequestParams requestParams) {
       return null;
    }

    @Override
    public Iterable<MediaListItem> findManyTargets(String sourceId, String fieldName, RequestParams requestParams) {
        Map<String, Object> selectParams = new HashMap<>();

        EmsClient emsClient = emsRouter.fetchEmsClientForEndpoint(this.getClass());
        List<MediaListItem> mediaListItems = (List<MediaListItem>) emsClient.callEmsList(selectParams, "media-list", sourceId + "/item", TypeFactory.defaultInstance().constructCollectionType(List.class,  MediaListItem.class));

        return mediaListItems;
    }
}
