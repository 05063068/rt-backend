package com.rottentomatoes.movieapi.domain.repository;

import com.rottentomatoes.movieapi.domain.model.MediaList;
import com.rottentomatoes.movieapi.domain.model.MediaListCategory;
import com.rottentomatoes.movieapi.domain.model.MediaListItem;
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
        selectParams.put("media_list_id", sourceId);

        List<MediaListItem> mediaListItems = sqlSession.selectList("com.rottentomatoes.movieapi.mappers.MediaListItemMapper.selectMediaListItemsForMediaList", selectParams);
        return mediaListItems;
    }
}
