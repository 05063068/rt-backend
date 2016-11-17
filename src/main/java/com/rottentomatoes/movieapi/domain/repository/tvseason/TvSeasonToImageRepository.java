package com.rottentomatoes.movieapi.domain.repository.tvseason;

import com.rottentomatoes.movieapi.domain.model.Image;
import com.rottentomatoes.movieapi.domain.model.TvSeason;
import com.rottentomatoes.movieapi.domain.repository.AbstractRepository;
import com.rottentomatoes.movieapi.domain.repository.ems.EmsClient;
import io.katharsis.queryParams.RequestParams;
import io.katharsis.repository.MetaRepository;
import io.katharsis.repository.RelationshipRepository;
import io.katharsis.response.MetaInformation;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by patrick on 9/22/16.
 */
@SuppressWarnings("rawtypes")
@Component
public class TvSeasonToImageRepository extends AbstractRepository implements RelationshipRepository<TvSeason, String, Image, String>, MetaRepository {
    @Override
    public MetaInformation getMetaInformation(Object root, Iterable resources, RequestParams requestParams, Serializable castedResourceId) {
        return null;
    }

    @Override
    public void setRelation(TvSeason source, String targetId, String fieldName) {

    }

    @Override
    public void setRelations(TvSeason source, Iterable<String> targetIds, String fieldName) {

    }

    @Override
    public void addRelations(TvSeason source, Iterable<String> targetIds, String fieldName) {

    }

    @Override
    public void removeRelations(TvSeason source, Iterable<String> targetIds, String fieldName) {

    }

    @Override
    public Image findOneTarget(String tvSeasonId, String fieldName, RequestParams requestParams) {
        Map<String, Object> selectParams = new HashMap<>();
        EmsClient emsClient = emsRouter.fetchEmsClientForEndpoint(this.getClass());
        Image tvImage = (Image)emsClient.callEmsEntity(selectParams, "tv/season", tvSeasonId + "/main-image", Image.class);
        return tvImage;
    }

    @Override
    public Iterable<Image> findManyTargets(String sourceId, String fieldName, RequestParams requestParams) {
        return null;
    }
}
