package com.rottentomatoes.movieapi.domain.repository.tvseason;

import com.fasterxml.jackson.databind.type.TypeFactory;
import com.rottentomatoes.movieapi.domain.ems.EmsClient;
import com.rottentomatoes.movieapi.domain.model.Franchise;
import com.rottentomatoes.movieapi.domain.model.TvSeason;
import com.rottentomatoes.movieapi.domain.model.TvSeries;
import com.rottentomatoes.movieapi.domain.repository.AbstractRepository;
import io.katharsis.queryParams.RequestParams;
import io.katharsis.repository.RelationshipRepository;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class TvSeasonToFranchiseRepository extends AbstractRepository implements RelationshipRepository<TvSeason, String, Franchise, String> {


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
    public Franchise findOneTarget(String tvSeasonId, String fieldName, RequestParams requestParams) {
        Map<String, Object> selectParams = new HashMap<>();
        EmsClient emsClient = emsRouter.fetchEmsClientForEndpoint(this.getClass());
        List<Franchise> franchiseList = (List<Franchise>) emsClient.callEmsIdList(selectParams, "tv/season", tvSeasonId + "/franchise", "franchise",
                TypeFactory.defaultInstance().constructCollectionType(List.class, Franchise.class));

        // Necessary because endpoint returns a list of 1 element
        if (franchiseList != null && franchiseList.size() > 0) {
            return franchiseList.get(0);
        }
        return null;
    }

    @Override
    public Iterable<Franchise> findManyTargets(String sourceId, String fieldName, RequestParams requestParams) {
        return null;
    }
}
