package com.rottentomatoes.movieapi.domain.repository;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.rottentomatoes.movieapi.domain.model.TvSeries;
import com.rottentomatoes.movieapi.search.SearchQuery;
import io.katharsis.queryParams.RequestParams;
import io.katharsis.repository.MetaRepository;
import io.katharsis.repository.ResourceRepository;
import io.katharsis.response.MetaInformation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        EmsClient emsClient = emsConfig.fetchEmsClientForEndpoint("tv/series");
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
        PreEmsClient preEmsClient = new PreEmsClient<TvSeries>(preEmsConfig);

        Map<String, Object> selectParams = new HashMap<>();
        List<TvSeries> tvSeries;
        List<Long> tvSeriesIds = new ArrayList<>();

        if (requestParams.getFilters() != null && requestParams.getFilters().get("search") != null) {
            if (requestParams.getFilters().get("search") instanceof Map) {
                Map<String, Object> searchObj = (Map<String, Object>) requestParams.getFilters().get("search");

                SearchQuery q = new SearchQuery("tv-series", searchObj);
                JsonNode json = q.execute();
                ArrayNode resultArr = (ArrayNode) json.path("results");
                tvSeriesIds = new ArrayList<>();
                for (JsonNode movie : resultArr) {
                    tvSeriesIds.add(Long.parseLong(movie.path("id").textValue()));
                }
                selectParams.put("ids", StringUtils.join(tvSeriesIds,","));
            } else {
                throw new IllegalArgumentException("Invalid search query.");
            }
        }

        if(tvSeriesIds.size() > 0) {
            tvSeries = (List<TvSeries>) preEmsClient.callPreEmsList(selectParams, "tv-series", null, TypeFactory.defaultInstance().constructCollectionType(List.class, TvSeries.class));
        }
        else{
            tvSeries = new ArrayList<>();
        }
        return tvSeries;
    }

    @Override
    public Iterable<TvSeries> findAll(Iterable<String> iterable, RequestParams requestParams) {
        return null;
    }

}
