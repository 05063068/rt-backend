package com.rottentomatoes.movieapi.domain.repository.tvseries;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.rottentomatoes.movieapi.domain.model.TvSeries;
import com.rottentomatoes.movieapi.domain.repository.AbstractRepository;
import com.rottentomatoes.movieapi.domain.ems.EmsClient;
import com.rottentomatoes.movieapi.utils.SearchUtils;
import io.katharsis.queryParams.RequestParams;
import io.katharsis.repository.MetaRepository;
import io.katharsis.repository.ResourceRepository;
import io.katharsis.response.MetaDataEnabledList;
import io.katharsis.response.MetaInformation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.rottentomatoes.movieapi.utils.SearchUtils.loadSearchMeta;

@SuppressWarnings("rawtypes")
@Component
public class TvSeriesRepository extends AbstractRepository implements ResourceRepository<TvSeries, String>, MetaRepository {

    @Override
    public void delete(String s) {}

    @Override
    public <S extends TvSeries> S save(S s) {return null;}

    @Override
    public MetaInformation getMetaInformation(Object o, Iterable iterable, RequestParams requestParams, Serializable serializable) {
        return null;
    }

    @Override
    public TvSeries findOne(String tvSeriesId, RequestParams requestParams) {
        Map<String, Object> selectParams = new HashMap<>();
        EmsClient emsClient = emsRouter.fetchEmsClientForEndpoint(this.getClass());
        List<TvSeries> series = (List<TvSeries>) emsClient.callEmsList(selectParams, "tv/series", tvSeriesId,
                TypeFactory.defaultInstance().constructCollectionType(List.class, TvSeries.class));

        // Necessary because endpoint returns a list of 1 element
        if (series != null && series.size() > 0) {
            return series.get(0);
        }
        return null;
    }

    @Override
    public Iterable<TvSeries> findAll(RequestParams requestParams) {
        EmsClient emsClient = emsRouter.fetchEmsClientForEndpoint(this.getClass());

        Map<String, Object> selectParams = new HashMap<>();
        MetaDataEnabledList<TvSeries> tvSeries = null;

        if (requestParams.getFilters() != null && requestParams.getFilters().get("search") != null) {
            List<Long> tvSeriesIds;
            String idList = null;
            JsonNode json;

            if (requestParams.getFilters().get("search") instanceof Map) {
                json = SearchUtils.callSearchService("tv-series", requestParams);
                tvSeriesIds = new ArrayList<>();
                if (json != null) {
                    ArrayNode resultArr = (ArrayNode) json.path("results");
                    for (JsonNode series : resultArr) {
                        tvSeriesIds.add(Long.parseLong(series.path("id").textValue()));
                    }
                    idList = StringUtils.join(tvSeriesIds, ",");
                }
            }
            else {
                throw new IllegalArgumentException("Invalid search query.");
            }

            if(tvSeriesIds.size() > 0) {
                tvSeries = new MetaDataEnabledList<>((List<TvSeries>) emsClient.callEmsList(selectParams, "tv/series", idList, TypeFactory.defaultInstance().constructCollectionType(List.class, TvSeries.class)));
                tvSeries.setMetaInformation(loadSearchMeta(json, requestParams));
            }
            else{
                tvSeries = new MetaDataEnabledList<>(new ArrayList<>());
            }
        }

        return tvSeries;
    }

    @Override
    public Iterable<TvSeries> findAll(Iterable<String> iterable, RequestParams requestParams) {
        return null;
    }

}
