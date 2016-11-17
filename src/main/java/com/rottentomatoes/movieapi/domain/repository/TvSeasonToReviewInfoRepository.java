package com.rottentomatoes.movieapi.domain.repository;

import com.fasterxml.jackson.databind.type.TypeFactory;
import com.rottentomatoes.movieapi.domain.meta.RelatedMetaDataInformation;
import com.rottentomatoes.movieapi.domain.model.Image;
import com.rottentomatoes.movieapi.domain.model.Review;
import com.rottentomatoes.movieapi.domain.model.ReviewInfo;
import com.rottentomatoes.movieapi.domain.model.TvEpisode;
import com.rottentomatoes.movieapi.domain.model.TvSeason;
import io.katharsis.queryParams.RequestParams;
import io.katharsis.repository.RelationshipRepository;
import io.katharsis.response.MetaDataEnabledList;
import io.katharsis.response.MetaInformation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("rawtypes")
@Component
public class TvSeasonToReviewInfoRepository extends AbstractRepository implements RelationshipRepository<TvSeason, String, ReviewInfo, String> {

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
    public ReviewInfo findOneTarget(String tvSeasonId, String fieldName, RequestParams requestParams) {
        Map<String, Object> selectParams = new HashMap<>();
        selectParams.put("limit", getLimit(fieldName, requestParams));
        selectParams.put("offset", getOffset(fieldName, requestParams));
        selectParams.put("country", getCountry(requestParams).getCountryCode());

        EmsClient emsClient = emsRouter.fetchEmsClientForEndpoint(this.getClass());
        Map jsonResponse = (Map) emsClient.callEmsEntity(selectParams, "tv/season", tvSeasonId + "/review", Map.class);

        if (jsonResponse != null && jsonResponse.keySet().size() > 0) {
            List<Review> reviewList = null;
            List<Integer> idList = (List) jsonResponse.get("review");
            HashMap<String, Object> counts = (HashMap) jsonResponse.get("counts");
            if (idList != null && idList instanceof List && idList.size() > 0) {
                String reviewIds = StringUtils.join(idList, ",");
                reviewList = (List<Review>) emsClient.callEmsList(selectParams, "tv/review", reviewIds,
                        TypeFactory.defaultInstance().constructCollectionType(List.class, Review.class));
            }
            return new ReviewInfo(counts, reviewList);
        }
        return null;
    }

    @Override
    public List<ReviewInfo> findManyTargets(String tvEpisodeId, String fieldName, RequestParams requestParams) {
        return null;
    }
}
