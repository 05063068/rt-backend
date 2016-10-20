
package com.rottentomatoes.movieapi.domain.repository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.node.IntNode;
import com.rottentomatoes.movieapi.domain.meta.RootMetaDataInformation;

import com.rottentomatoes.movieapi.search.SearchQuery;
import io.katharsis.repository.MetaRepository;
import io.katharsis.response.MetaInformation;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.rottentomatoes.movieapi.domain.model.Movie;

import io.katharsis.queryParams.RequestParams;
import io.katharsis.repository.ResourceRepository;

import com.rottentomatoes.movieapi.utils.RepositoryUtils;

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
        EmsClient emsClient = emsConfig.fetchEmsClient("movie");
        Movie movie = (Movie) emsClient.callEmsEntity(selectParams, "movie", movieId, Movie.class);
        return movie;
    }

    @Override
    public Iterable<Movie> findAll(RequestParams requestParams) {
        PreEmsClient preEmsClient = new PreEmsClient<Movie>(preEmsConfig);
        Map<String, Object> selectParams = new HashMap<>();
        List<Movie> movies;
        List<Long> movieIds = new ArrayList<>();

        if (requestParams.getFilters() != null && requestParams.getFilters().get("search") != null) {
            if(requestParams.getFilters().get("search") instanceof Map){
                JsonNode json = callSearchService(requestParams);
                ArrayNode resultArr = (ArrayNode) json.path("results");
                movieIds = new ArrayList<>();
                for (JsonNode movie : resultArr) {
                    movieIds.add(Long.parseLong(movie.path("id").textValue()));
                }
                selectParams.put("ids", StringUtils.join(movieIds,","));
            }
            else{
                throw new IllegalArgumentException("Invalid search query.");
            }
        }

        //  Hydrate results
        selectParams.put("country", getCountry(requestParams).getCountryCode());
        if(movieIds.size() > 0) {
            movies = (List<Movie>) preEmsClient.callPreEmsList(selectParams, "movie", null, TypeFactory.defaultInstance().constructCollectionType(List.class, Movie.class));
            return movies;
        } else {
            return null;
        }
    }

    @Override
    public Iterable<Movie> findAll(Iterable<String> ids, RequestParams requestParams) {
        return null;
    }

    @Override
    public MetaInformation getMetaInformation(Object root, Iterable resources, RequestParams requestParams, Serializable castedResourceId) {
        RootMetaDataInformation metaData = new RootMetaDataInformation();
        metaData.setRequestParams(requestParams);

        if (requestParams.getFilters() != null && requestParams.getFilters().get("search") != null) {
            if(requestParams.getFilters().get("search") instanceof Map){
                JsonNode json = callSearchService(requestParams);
                IntNode found = (IntNode) json.path("found");
                metaData.setTotalCount(Integer.parseInt(found.toString()));
            }
        }
        return metaData;
    }

    private JsonNode callSearchService(RequestParams requestParams) {
            Map<String, Object> searchObj = (Map<String, Object>) requestParams.getFilters().get("search");
            SearchQuery q = new SearchQuery("movies", searchObj);
            JsonNode json = q.execute();

        return json;
    }
}
