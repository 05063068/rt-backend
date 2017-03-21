package com.rottentomatoes.movieapi.domain.repository.movie;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.type.TypeFactory;
import com.rottentomatoes.movieapi.domain.repository.AbstractRepository;
import com.rottentomatoes.movieapi.domain.clients.ems.EmsClient;
import com.rottentomatoes.movieapi.utils.RepositoryUtils;
import org.springframework.stereotype.Component;

import com.rottentomatoes.movieapi.domain.model.meta.RelatedMetaDataInformation;
import com.rottentomatoes.movieapi.domain.model.Image;
import com.rottentomatoes.movieapi.domain.model.Movie;

import io.katharsis.queryParams.RequestParams;
import io.katharsis.repository.MetaRepository;
import io.katharsis.repository.RelationshipRepository;
import io.katharsis.response.MetaDataEnabledList;
import io.katharsis.response.MetaInformation;

@SuppressWarnings("rawtypes")
@Component
public class MovieToImageRepository extends AbstractRepository implements RelationshipRepository<Movie, String, Image, String>, MetaRepository {

    @Override
    public void addRelations(Movie arg0, Iterable<String> arg1, String arg2) {
    }

    @Override
    public void removeRelations(Movie arg0, Iterable<String> arg1, String arg2) {
    }

    @Override
    public void setRelation(Movie arg0, String arg1, String arg2) {
    }

    @Override
    public void setRelations(Movie arg0, Iterable<String> arg1, String arg2) {
    }

    @Override
    public Image findOneTarget(String id, String fieldName, RequestParams requestParams) {
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public MetaDataEnabledList<Image> findManyTargets(String movieId, String fieldName, RequestParams requestParams) {
        Map<String, Object> selectParams = new HashMap<>();
        selectParams.put("limit", RepositoryUtils.getLimit(fieldName, requestParams));
        selectParams.put("offset", RepositoryUtils.getOffset(fieldName, requestParams));
        EmsClient emsClient = emsRouter.fetchEmsClientForEndpoint(this.getClass());
        List<Image> rawImageList = (List<Image>)emsClient.callEmsList(selectParams, "movie", movieId + "/image", TypeFactory.defaultInstance().constructCollectionType(List.class,  Image.class));
        return new MetaDataEnabledList(rawImageList);
    }

    @Override
    public MetaInformation getMetaInformation(Object root, Iterable resources, Serializable castedResourceId, String fieldName, RequestParams requestParams) {
        Map<String, Object> selectParams = new HashMap<>();

        EmsClient emsClient = emsRouter.fetchEmsClientForEndpoint(this.getClass());
        RelatedMetaDataInformation metaData = (RelatedMetaDataInformation) emsClient.callEmsEntity(selectParams, "movie", castedResourceId + "/image/meta", RelatedMetaDataInformation.class);
        if (root instanceof RelationshipRepository) {
            metaData.setRequestParams(requestParams);
        }
        return metaData;
    }
}
