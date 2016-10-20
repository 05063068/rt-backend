package com.rottentomatoes.movieapi.domain.repository;

import com.fasterxml.jackson.databind.type.TypeFactory;
import com.rottentomatoes.movieapi.domain.model.MediaList;

import io.katharsis.queryParams.RequestParams;
import io.katharsis.repository.ResourceRepository;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.rottentomatoes.movieapi.utils.RepositoryUtils.getCountry;

@Component
public class MediaListRepository extends AbstractRepository implements ResourceRepository<MediaList, String> {

    @Override
    public MediaList findOne(String id, RequestParams requestParams) {
        Map<String, Object> selectParams = new HashMap<>();

        selectParams.put("country", getCountry(requestParams).getCountryCode());

        EmsClient emsClient = emsConfig.fetchEmsClient("media-list");
        MediaList mediaList = (MediaList)emsClient.callEmsEntity(selectParams, "media-list", id, MediaList.class);
        return mediaList;
    }

    @Override
    public Iterable<MediaList> findAll(RequestParams requestParams) {
        Map<String, Object> selectParams = new HashMap<>();

        selectParams.put("limit", getLimit("", requestParams));
        selectParams.put("offset", getOffset("", requestParams));
        selectParams.put("country", getCountry(requestParams).getCountryCode());

        EmsClient emsClient = emsConfig.fetchEmsClient("media-list");
        List<MediaList> mediaLists = (List<MediaList>)emsClient.callEmsList(selectParams, "media-list", null, TypeFactory.defaultInstance().constructCollectionType(List.class,  MediaList.class));
  
        return mediaLists;
    }

    @Override
    public Iterable<MediaList> findAll(Iterable<String> strings, RequestParams requestParams) {
        return null;
    }

    @Override
    public void delete(String s) {

    }

    @Override
    public <S extends MediaList> S save(S entity) {
        return null;
    }
}
