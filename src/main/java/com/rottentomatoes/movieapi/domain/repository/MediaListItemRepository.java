package com.rottentomatoes.movieapi.domain.repository;

import com.rottentomatoes.movieapi.domain.model.MediaListItem;
import io.katharsis.queryParams.RequestParams;
import io.katharsis.repository.ResourceRepository;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import static com.rottentomatoes.movieapi.utils.RepositoryUtils.getCountry;

@Component
public class MediaListItemRepository extends AbstractRepository implements ResourceRepository<MediaListItem, String> {
    @Override
    public MediaListItem findOne(String itemId, RequestParams requestParams) {
        Map<String, Object> selectParams = new HashMap<>();

        selectParams.put("id", itemId);
        selectParams.put("limit", getLimit("", requestParams));
        selectParams.put("limit", getLimit("", requestParams));
        selectParams.put("offset", getOffset("", requestParams));
        selectParams.put("country", getCountry(requestParams).getCountryCode());

        EmsClient emsClient = emsConfig.fetchEmsClient("media-item");
        MediaListItem mediaListItem = (MediaListItem)emsClient.callEmsEntity(selectParams, "media-item", itemId, MediaListItem.class);

        return mediaListItem;
    }

    @Override
    public Iterable<MediaListItem> findAll(RequestParams requestParams) {
        return null;
    }

    @Override
    public Iterable<MediaListItem> findAll(Iterable<String> strings, RequestParams requestParams) {
        return null;
    }

    @Override
    public void delete(String s) {

    }

    @Override
    public <S extends MediaListItem> S save(S entity) {
        return null;
    }
}
