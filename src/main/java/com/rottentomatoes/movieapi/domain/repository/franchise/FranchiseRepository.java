package com.rottentomatoes.movieapi.domain.repository.franchise;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.rottentomatoes.movieapi.domain.model.Franchise;
import com.rottentomatoes.movieapi.domain.model.Movie;
import com.rottentomatoes.movieapi.domain.model.TvSeries;
import com.rottentomatoes.movieapi.domain.repository.AbstractRepository;
import com.rottentomatoes.movieapi.domain.ems.EmsClient;
import com.rottentomatoes.movieapi.domain.repository.movie.MovieRepository;
import com.rottentomatoes.movieapi.domain.repository.tvseries.TvSeriesRepository;
import com.rottentomatoes.movieapi.utils.SearchUtils;
import io.katharsis.response.MetaDataEnabledList;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import io.katharsis.queryParams.RequestParams;
import io.katharsis.repository.ResourceRepository;
import static com.rottentomatoes.movieapi.utils.SearchUtils.loadSearchMeta;

@SuppressWarnings("rawtypes")
@Component
public class FranchiseRepository extends AbstractRepository implements ResourceRepository<Franchise, String> {

    @Override
    public <S extends Franchise> S save(S entity) {
        return null;
    }

    @Override
    public void delete(String id) {

    }

    @Override
    public Franchise findOne(String id, RequestParams requestParams) {

        Map<String, Object> selectParams = new HashMap<>();

        EmsClient emsClient = emsRouter.fetchEmsClientForEndpoint(this.getClass());
        List<Franchise> franchises = (List<Franchise>) emsClient.callEmsList(selectParams,
                "franchise", id,
                TypeFactory.defaultInstance().constructCollectionType(List.class, Franchise.class));

        Franchise franchise;
        // Necessary because endpoint returns a list of 1 element
        if (franchises != null && franchises.size() > 0) {
            franchise = franchises.get(0);

            // Get top movie associated with id field
//            if (franchise != null && franchise.getTopMovieId() != null) {
//                emsClient = emsRouter.fetchEmsClientForEndpoint(MovieRepository.class);
//                franchise.setTopMovie((Movie) emsClient.callEmsEntity(selectParams, "movie", franchise.getTopMovieId(), Movie.class));
//            }

            // Get top series associated with id field
//            if (franchise != null && franchise.getTopTvSeriesId() != null) {
//                emsClient = emsRouter.fetchEmsClientForEndpoint(TvSeriesRepository.class);
//                List<TvSeries> series = (List<TvSeries>) emsClient.callEmsList(selectParams, "tv/series", franchise.getTopTvSeriesId(), TypeFactory.defaultInstance().constructCollectionType(List.class, TvSeries.class));
//
//                if (series != null && series.size() > 0) {
//                    franchise.setTopTvSeries(series.get(0));
//                }
//            }
            return franchise;
        }
        return null;
    }

    @Override
    public Iterable<Franchise> findAll(RequestParams requestParams) {
        EmsClient emsClient = emsRouter.fetchEmsClientForEndpoint(this.getClass());
        Map<String, Object> selectParams = new HashMap<>();
        MetaDataEnabledList<Franchise> franchises = null;

        if (requestParams.getFilters() != null && requestParams.getFilters().get("search") != null) {
            List<Long> franchiseIds = new ArrayList<>();
            JsonNode json;

            if (requestParams.getFilters().get("search") instanceof Map) {
                json = SearchUtils.callSearchService("franchises", requestParams);
                if (json != null) {
                    ArrayNode resultArr = (ArrayNode) json.path("results");
                    for (JsonNode movie : resultArr) {
                        franchiseIds.add(Long.parseLong(movie.path("id").textValue()));
                    }
                }
            } else {
                throw new IllegalArgumentException("Invalid search query.");
            }

            //  Hydrate results
            if (franchiseIds.size() > 0) {
                franchises = new MetaDataEnabledList<>((List<Franchise>) emsClient.callEmsList(selectParams, "franchise", StringUtils.join(franchiseIds, ","), TypeFactory.defaultInstance().constructCollectionType(List.class, Franchise.class)));
                franchises.setMetaInformation(loadSearchMeta(json, requestParams));
            }
        }
        else {
            franchises = new MetaDataEnabledList<>(new ArrayList<>());
        }

        return franchises;
    }

    @Override
    public Iterable<Franchise> findAll(Iterable<String> ids, RequestParams requestParams) {
        // TODO Auto-generated method stub
        return null;
    }

}
