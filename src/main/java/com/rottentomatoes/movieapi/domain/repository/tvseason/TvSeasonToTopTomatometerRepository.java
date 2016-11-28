package com.rottentomatoes.movieapi.domain.repository.tvseason;

import com.fasterxml.jackson.databind.type.TypeFactory;
import com.rottentomatoes.movieapi.domain.model.TopTomatometer;
import com.rottentomatoes.movieapi.domain.model.TvSeason;
import com.rottentomatoes.movieapi.domain.repository.AbstractRepository;
import com.rottentomatoes.movieapi.domain.ems.EmsClient;
import com.rottentomatoes.movieapi.utils.RepositoryUtils;
import io.katharsis.queryParams.RequestParams;
import io.katharsis.repository.RelationshipRepository;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class TvSeasonToTopTomatometerRepository extends AbstractRepository implements RelationshipRepository<TvSeason, String, TopTomatometer, String> {

    private static final String CRITIC_TYPE = "criticType";
    private static final String TOP_CRITICS = "top";

    @Override
    public void setRelation(TvSeason tvSeason, String s, String s2) {

    }

    @Override
    public void setRelations(TvSeason tvSeason, Iterable<String> iterable, String s) {

    }

    @Override
    public void addRelations(TvSeason tvSeason, Iterable<String> iterable, String s) {

    }

    @Override
    public void removeRelations(TvSeason tvSeason, Iterable<String> iterable, String s) {

    }

    @Override
    public TopTomatometer findOneTarget(String tvSeasonId, String fieldName, RequestParams requestParams) {
        Map<String, Object> selectParams = new HashMap<>();
        selectParams.put("country", RepositoryUtils.getCountry(requestParams).getCountryCode());

        if (requestParams.getFilters() != null && requestParams.getFilters().containsKey(CRITIC_TYPE) && ((String) requestParams.getFilters().get(CRITIC_TYPE)).equalsIgnoreCase(TOP_CRITICS)) {
            selectParams.put(CRITIC_TYPE, TOP_CRITICS);
        }

        EmsClient emsClient = emsRouter.fetchEmsClientForEndpoint(this.getClass());
//        TopTomatometer topTomatometer = (TopTomatometer) emsClient.callEmsEntity(selectParams, "tv/season", tvSeasonId + "/top-tomatometer", TopTomatometer.class);

        List<TopTomatometer> topTomatometerList = (List<TopTomatometer>) emsClient.callEmsIdList(selectParams, "tv/season", tvSeasonId + "/top-tomatometer", "tv/season",
                TypeFactory.defaultInstance().constructCollectionType(List.class, TopTomatometer.class));

        // Necessary because endpoint returns a list of 1 element
        if (topTomatometerList != null && topTomatometerList.size() > 0) {
            return topTomatometerList.get(0);
        }
        return null;
    }

    @Override
    public Iterable<TopTomatometer> findManyTargets(String s, String s2, RequestParams requestParams) {
        return null;
    }
}
