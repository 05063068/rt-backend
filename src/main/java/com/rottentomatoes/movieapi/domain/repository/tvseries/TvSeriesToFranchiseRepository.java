package com.rottentomatoes.movieapi.domain.repository.tvseries;

import com.fasterxml.jackson.databind.type.TypeFactory;
import com.rottentomatoes.movieapi.domain.clients.ems.EmsClient;
import com.rottentomatoes.movieapi.domain.model.Franchise;
import com.rottentomatoes.movieapi.domain.model.TvSeries;
import com.rottentomatoes.movieapi.domain.repository.AbstractRepository;
import io.katharsis.queryParams.RequestParams;
import io.katharsis.repository.RelationshipRepository;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class TvSeriesToFranchiseRepository extends AbstractRepository implements RelationshipRepository<TvSeries, String, Franchise, String> {


    @Override
    public void setRelation(TvSeries source, String targetId, String fieldName) {

    }

    @Override
    public void setRelations(TvSeries source, Iterable<String> targetIds, String fieldName) {

    }

    @Override
    public void addRelations(TvSeries source, Iterable<String> targetIds, String fieldName) {

    }

    @Override
    public void removeRelations(TvSeries source, Iterable<String> targetIds, String fieldName) {

    }

    @Override
    public Franchise findOneTarget(String tvSeriesId, String fieldName, RequestParams requestParams) {
        Map<String, Object> selectParams = new HashMap<>();
        EmsClient emsClient = emsRouter.fetchEmsClientForEndpoint(this.getClass());
        List<Franchise> franchiseList = (List<Franchise>) emsClient.callEmsIdList(selectParams, "tv/series", tvSeriesId + "/franchise", "franchise",
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
