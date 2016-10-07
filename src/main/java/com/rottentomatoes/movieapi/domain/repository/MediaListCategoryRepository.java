package com.rottentomatoes.movieapi.domain.repository;

import com.rottentomatoes.movieapi.domain.model.MediaListCategory;
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

        selectParams.put("limit", getLimit("", requestParams));
        selectParams.put("offset", getOffset("", requestParams));
        selectParams.put("id", mediaCategoryId);

        // At the point of creation "LIV" was only distinct status in the table so we default to live.
        selectParams.put("status", LIVE_STATUS);

        PreEmsClient preEmsClient = new PreEmsClient(preEmsConfig);
        MediaListCategory mediaListCategory = (MediaListCategory)preEmsClient.callPreEmsEntity(selectParams, "media-list-category", mediaCategoryId, MediaListCategory.class);

        return mediaListCategory;
    }

    @Override
    public Iterable<MediaListCategory> findAll(RequestParams requestParams) {
        Map<String, Object> selectParams = new HashMap<>();

        selectParams.put("limit", getLimit("", requestParams));
        selectParams.put("offset", getOffset("", requestParams));

        // At the point of creation "LIV" was only distinct status in the table so we default to live.
        selectParams.put("status", LIVE_STATUS);

        PreEmsClient preEmsClient = new PreEmsClient<Iterable<MediaListCategory>>(preEmsConfig);
        List<MediaListCategory> mediaListCategories = (List<MediaListCategory>) preEmsClient.callPreEmsList(selectParams, "media-list-category", "/", TypeFactory.defaultInstance().constructCollectionType(List.class,  MediaListCategory.class));

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
