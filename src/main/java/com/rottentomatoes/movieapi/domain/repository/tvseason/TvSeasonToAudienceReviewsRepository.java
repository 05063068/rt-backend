package com.rottentomatoes.movieapi.domain.repository.tvseason;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.type.TypeFactory;
import com.rottentomatoes.movieapi.domain.repository.AbstractRepository;
import com.rottentomatoes.movieapi.domain.ems.EmsClient;
import com.rottentomatoes.movieapi.utils.RepositoryUtils;
import com.rottentomatoes.movieapi.utils.StringSanitizationUtils;

import org.springframework.stereotype.Component;

import com.rottentomatoes.movieapi.domain.meta.RelatedMetaDataInformation;
import com.rottentomatoes.movieapi.domain.model.TvAudienceReview;
import com.rottentomatoes.movieapi.domain.model.TvSeason;

import io.katharsis.queryParams.RequestParams;
import io.katharsis.repository.MetaRepository;
import io.katharsis.repository.RelationshipRepository;
import io.katharsis.response.MetaDataEnabledList;
import io.katharsis.response.MetaInformation;

@SuppressWarnings("rawtypes")
@Component
public class TvSeasonToAudienceReviewsRepository extends AbstractRepository implements RelationshipRepository<TvSeason, String, TvAudienceReview, String>, MetaRepository {


    @Override
    public void addRelations(TvSeason arg0, Iterable<String> arg1, String arg2) {
    }

    @Override
    public void removeRelations(TvSeason arg0, Iterable<String> arg1, String arg2) {
    }

    @Override
    public void setRelation(TvSeason arg0, String arg1, String arg2) {
    }

    @Override
    public void setRelations(TvSeason arg0, Iterable<String> arg1, String arg2) {
    }

    @Override
    public TvAudienceReview findOneTarget(String sourceId, String fieldName, RequestParams requestParams) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public MetaDataEnabledList<TvAudienceReview> findManyTargets(String tvSeasonId, String fieldName, RequestParams requestParams) {
        Map<String, Object> selectParams = new HashMap<>();
        MetaDataEnabledList<TvAudienceReview> reviewList = null;

          EmsClient emsClient = emsRouter.fetchEmsClientForEndpoint(this.getClass());
          List<TvAudienceReview> rawReviewList = (List<TvAudienceReview>)emsClient.callEmsList(selectParams, "tv/season", tvSeasonId + "/audience-reviews", TypeFactory.defaultInstance().constructCollectionType(List.class,  TvAudienceReview.class));
          StringSanitizationUtils.sanitizeTvAudienceReviews(rawReviewList);
          reviewList = new MetaDataEnabledList(rawReviewList);

          return reviewList;
    }

    @Override
    public MetaInformation getMetaInformation(Object root, Iterable resources, RequestParams requestParams, Serializable castedResourceId) {
        Map<String, Object> selectParams = new HashMap<>();
        RelatedMetaDataInformation metaData = new RelatedMetaDataInformation();
        metaData.setTotalCount(0);
        
        if (root instanceof RelationshipRepository) {
            metaData.setRequestParams(requestParams);
        }
        return metaData;
    }
}
