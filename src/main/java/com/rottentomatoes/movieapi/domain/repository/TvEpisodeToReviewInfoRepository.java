package com.rottentomatoes.movieapi.domain.repository;

import com.fasterxml.jackson.databind.type.TypeFactory;
import com.rottentomatoes.movieapi.domain.model.Review;
import com.rottentomatoes.movieapi.domain.model.ReviewInfo;
import com.rottentomatoes.movieapi.domain.model.TvEpisode;
import io.katharsis.queryParams.RequestParams;
import io.katharsis.repository.RelationshipRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("rawtypes")
@Component
public class TvEpisodeToReviewInfoRepository extends AbstractRepository implements RelationshipRepository<TvEpisode, String, ReviewInfo, String> {

    private static final String CRITIC_TYPE = "criticType";
    private static final String TOP_CRITICS = "top";


    @Override
    public void addRelations(TvEpisode arg0, Iterable<String> arg1, String arg2) {
    }

    @Override
    public void removeRelations(TvEpisode arg0, Iterable<String> arg1, String arg2) {
    }

    @Override
    public void setRelation(TvEpisode arg0, String arg1, String arg2) {
    }

    @Override
    public void setRelations(TvEpisode arg0, Iterable<String> arg1, String arg2) {
    }

    @Override
    public ReviewInfo findOneTarget(String tvEpisodeId, String fieldName, RequestParams requestParams) {
        Map<String, Object> selectParams = new HashMap<>();
        selectParams.put("limit", getLimit(fieldName, requestParams));
        selectParams.put("offset", getOffset(fieldName, requestParams));
        selectParams.put("country", getCountry(requestParams).getCountryCode());

        EmsClient emsClient = emsRouter.fetchEmsClientForEndpoint(this.getClass());
        Map jsonResponse = (Map) emsClient.callEmsEntity(selectParams, "tv/episode", tvEpisodeId + "/review", Map.class);

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
