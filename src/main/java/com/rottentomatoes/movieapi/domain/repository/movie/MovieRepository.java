
package com.rottentomatoes.movieapi.domain.repository.movie;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.type.TypeFactory;

import com.rottentomatoes.movieapi.domain.repository.AbstractRepository;
import com.rottentomatoes.movieapi.domain.ems.EmsClient;
import com.rottentomatoes.movieapi.utils.SearchUtils;
import io.katharsis.repository.MetaRepository;
import io.katharsis.response.MetaDataEnabledList;
import io.katharsis.response.MetaInformation;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.rottentomatoes.movieapi.domain.model.Movie;

import io.katharsis.queryParams.RequestParams;
import io.katharsis.repository.ResourceRepository;

import com.rottentomatoes.movieapi.utils.RepositoryUtils;

import static com.rottentomatoes.movieapi.utils.SearchUtils.loadSearchMeta;

@SuppressWarnings("rawtypes")
@Component
public class MovieRepository extends AbstractRepository implements ResourceRepository<Movie, String>, MetaRepository {

    @Override
    public <S extends Movie> S save(S entity) {
        return null;
    }

    @Override
    public void delete(String Long) {
    }

    @Override
    @JsonCreator
    public Movie findOne(String movieId, RequestParams requestParams) {
        Map<String, Object> selectParams = new HashMap<>();
        RepositoryUtils.setMovieParams(selectParams, requestParams);
        EmsClient emsClient = emsRouter.fetchEmsClientForEndpoint(this.getClass());
        Movie movie = (Movie) emsClient.callEmsEntity(selectParams, "movie", movieId, Movie.class);
        return movie;
    }

    @Override
    public Iterable<Movie> findAll(RequestParams requestParams) {
        EmsClient emsClient = emsRouter.fetchEmsClientForEndpoint(this.getClass());
        Map<String, Object> selectParams = new HashMap<>();
        MetaDataEnabledList<Movie> movies = null;

        if (requestParams.getFilters() != null && requestParams.getFilters().get("search") != null) {
            List<Long> movieIds = new ArrayList<>();
            JsonNode json;

            if (requestParams.getFilters().get("search") instanceof Map) {
                json = SearchUtils.callSearchService("movies", requestParams);
                if (json != null) {
                    ArrayNode resultArr = (ArrayNode) json.path("results");
                    for (JsonNode movie : resultArr) {
                        movieIds.add(Long.parseLong(movie.path("id").textValue()));
                    }
                    selectParams.put("ids", StringUtils.join(movieIds, ","));
                }
            } else {
                throw new IllegalArgumentException("Invalid search query.");
            }

            //  Hydrate results
            selectParams.put("country", RepositoryUtils.getCountry(requestParams).getCountryCode());
            if (movieIds.size() > 0) {
                movies = new MetaDataEnabledList<>((List<Movie>) emsClient.callEmsList(selectParams, "movie", null, TypeFactory.defaultInstance().constructCollectionType(List.class, Movie.class)));
                movies.setMetaInformation(loadSearchMeta(json, requestParams));
            } else {
                return null;
            }
        }
        return movies;
    }

    @Override
    public Iterable<Movie> findAll(Iterable<String> ids, RequestParams requestParams) {
        return null;
    }

    @Override
    public MetaInformation getMetaInformation(Object root, Iterable resources, Serializable castedResourceId, String fieldName, RequestParams requestParams) {
        return null;
    }

}