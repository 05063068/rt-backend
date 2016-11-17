package com.rottentomatoes.movieapi.domain.repository.franchise;

import com.fasterxml.jackson.databind.type.TypeFactory;
import com.rottentomatoes.movieapi.domain.model.Franchise;
import com.rottentomatoes.movieapi.domain.model.TvSeries;
import com.rottentomatoes.movieapi.domain.repository.AbstractRepository;
import com.rottentomatoes.movieapi.domain.ems.EmsClient;
import io.katharsis.queryParams.RequestParams;
import io.katharsis.repository.RelationshipRepository;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class FranchiseToTvSeriesRepository extends AbstractRepository implements RelationshipRepository<Franchise, String, TvSeries, String> {

    @Override
    public void addRelations(Franchise arg0, Iterable<String> arg1, String arg2) {
    }

    @Override
    public void removeRelations(Franchise arg0, Iterable<String> arg1, String arg2) {
    }

    @Override
    public void setRelation(Franchise arg0, String TvSeries, String arg2) {
    }

    @Override
    public void setRelations(Franchise arg0, Iterable<String> arg1, String arg2) {
    }

    @Override
    public TvSeries findOneTarget(String id, String fieldName, RequestParams requestParams) {
        return null;
    }

    @Override
    public Iterable<TvSeries> findManyTargets(String franchiseId, String fieldName, RequestParams requestParams) {
        Map<String, Object> selectParams = new HashMap<>();
        selectParams.put("limit", getLimit(fieldName, requestParams));

        EmsClient emsClient = emsRouter.fetchEmsClientForEndpoint(this.getClass());
        List<String> tvSeriesIds = (List<String>) emsClient.callEmsList(selectParams, "franchise", franchiseId + "/series",
                TypeFactory.defaultInstance().constructCollectionType(List.class,  String.class));

        emsClient = emsRouter.fetchEmsClientForEndpoint(this.getClass());
        if (tvSeriesIds != null && tvSeriesIds.size() > 0) {
            String ids = String.join(",", tvSeriesIds);
            List<TvSeries> tvSeriesList = (List<TvSeries>) emsClient.callEmsList(selectParams, "tv/series", ids,
                    TypeFactory.defaultInstance().constructCollectionType(List.class, TvSeries.class));
            return tvSeriesList;
        }
        return null;
    }
}
