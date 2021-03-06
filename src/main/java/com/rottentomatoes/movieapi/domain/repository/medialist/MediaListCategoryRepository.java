package com.rottentomatoes.movieapi.domain.repository.medialist;

import com.fasterxml.jackson.databind.type.TypeFactory;
import com.rottentomatoes.movieapi.domain.model.MediaListCategory;

import com.rottentomatoes.movieapi.domain.repository.AbstractRepository;
import com.rottentomatoes.movieapi.domain.clients.ems.EmsClient;
import com.rottentomatoes.movieapi.utils.RepositoryUtils;
import io.katharsis.queryParams.RequestParams;
import io.katharsis.repository.ResourceRepository;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class MediaListCategoryRepository extends AbstractRepository implements ResourceRepository<MediaListCategory, String> {
    private static String LIVE_STATUS = "LIV";

    @Override
    public MediaListCategory findOne(String mediaCategoryId, RequestParams requestParams) {
        Map<String, Object> selectParams = new HashMap<>();

        selectParams.put("limit", RepositoryUtils.getLimit("", requestParams));
        selectParams.put("offset", RepositoryUtils.getOffset("", requestParams));
        selectParams.put("id", mediaCategoryId);

        // At the point of creation "LIV" was only distinct status in the table so we default to live.
        selectParams.put("status", LIVE_STATUS);

        EmsClient emsClient = emsRouter.fetchEmsClientForEndpoint(this.getClass());
        MediaListCategory mediaListCategory = (MediaListCategory)emsClient.callEmsEntity(selectParams, "media-list-category", mediaCategoryId, MediaListCategory.class);

        return mediaListCategory;
    }

    @Override
    public Iterable<MediaListCategory> findAll(RequestParams requestParams) {
        Map<String, Object> selectParams = new HashMap<>();

        selectParams.put("limit", RepositoryUtils.getLimit("", requestParams));
        selectParams.put("offset", RepositoryUtils.getOffset("", requestParams));

        // At the point of creation "LIV" was only distinct status in the table so we default to live.
        selectParams.put("status", LIVE_STATUS);

        EmsClient emsClient = emsRouter.fetchEmsClientForEndpoint(this.getClass());
        List<MediaListCategory> mediaListCategories = (List<MediaListCategory>) emsClient.callEmsList(selectParams, "media-list-category", "/", TypeFactory.defaultInstance().constructCollectionType(List.class,  MediaListCategory.class));

        return mediaListCategories;
    }

    @Override
    public Iterable<MediaListCategory> findAll(Iterable<String> strings, RequestParams requestParams) {
        return null;
    }

    @Override
    public void delete(String s) {

    }

    @Override
    public <S extends MediaListCategory> S save(S entity) {
        return null;
    }

}
