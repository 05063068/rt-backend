package com.rottentomatoes.movieapi.domain.repository;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.type.TypeFactory;
import com.rottentomatoes.movieapi.utils.RepositoryUtils;
import com.rottentomatoes.movieapi.utils.SqlParameterUtils;
import org.springframework.stereotype.Component;

import com.rottentomatoes.movieapi.domain.meta.RootMetaDataInformation;
import com.rottentomatoes.movieapi.domain.model.Movie;
import com.rottentomatoes.movieapi.domain.model.MovieList;

import io.katharsis.queryParams.RequestParams;
import io.katharsis.repository.MetaRepository;
import io.katharsis.repository.RelationshipRepository;
import io.katharsis.resource.exception.ResourceNotFoundException;
import io.katharsis.response.MetaInformation;

import static com.rottentomatoes.movieapi.utils.RepositoryUtils.getCountry;
import static com.rottentomatoes.movieapi.utils.SqlParameterUtils.getTodayPST;
import static java.time.temporal.TemporalAdjusters.previous;

@Component
public class MovieListToMovieRepository extends AbstractRepository implements RelationshipRepository<MovieList, String, Movie, String>, MetaRepository<Movie> {

    @Override
    public void addRelations(MovieList arg0, Iterable<String> arg1, String arg2) {
    }

    @Override
    public void removeRelations(MovieList arg0, Iterable<String> arg1, String arg2) {
    }

    @Override
    public void setRelation(MovieList arg0, String arg1, String arg2) {
    }

    @Override
    public void setRelations(MovieList arg0, Iterable<String> arg1, String arg2) {
    }

    @Override
    public Movie findOneTarget(String id, String fieldName, RequestParams requestParams) {
        return null;
    }

    @Override
    public Iterable<Movie> findManyTargets(String listId, String fieldName, RequestParams requestParams) {
        Map<String, Object> selectParams = new HashMap<>();
        RepositoryUtils.setMovieParams(selectParams, requestParams);
        PreEmsClient preEmsClient = new PreEmsClient<List<Movie>>(preEmsConfig);

        selectParams.put("limit", getLimit("", requestParams));
        selectParams.put("offset", getOffset("", requestParams));
        selectParams.put("country", getCountry(requestParams).getCountryCode());

        switch (listId) {
            case "top-box-office":
                selectParams = SqlParameterUtils.setTopBoxOfficeParams(selectParams);
                List<Movie> movies = (List<Movie>) preEmsClient.callPreEmsList(selectParams, listId, null, TypeFactory.defaultInstance().constructCollectionType(List.class, Movie.class));
                if (movies.isEmpty()) {
                    movies = (List<Movie>) preEmsClient.callPreEmsList(selectParams, listId, "fallback", TypeFactory.defaultInstance().constructCollectionType(List.class, Movie.class));
                }
                return movies;

            case "upcoming":
                selectParams = SqlParameterUtils.setUpcomingParams(selectParams);
                return (List<Movie>) preEmsClient.callPreEmsList(selectParams, listId, null, TypeFactory.defaultInstance().constructCollectionType(List.class, Movie.class));

            case "opening":
                selectParams = SqlParameterUtils.setOpeningParams(selectParams);
                return (List<Movie>) preEmsClient.callPreEmsList(selectParams, listId, null, TypeFactory.defaultInstance().constructCollectionType(List.class, Movie.class));

            case "top-rentals":
                selectParams = SqlParameterUtils.setTopRentalsParams(selectParams);
                return (List<Movie>) preEmsClient.callPreEmsList(selectParams, listId, null, TypeFactory.defaultInstance().constructCollectionType(List.class, Movie.class));

            case "upcoming-dvds":
                selectParams = SqlParameterUtils.setUpcomingDvdsParam(selectParams);
                return (List<Movie>) preEmsClient.callPreEmsList(selectParams, listId, null, TypeFactory.defaultInstance().constructCollectionType(List.class, Movie.class));

            case "new-on-dvd":
                selectParams = SqlParameterUtils.setNewOnDvdParams(selectParams);
                return (List<Movie>) preEmsClient.callPreEmsList(selectParams, listId, null, TypeFactory.defaultInstance().constructCollectionType(List.class, Movie.class));

            default:
                throw new ResourceNotFoundException("Invalid list type");
        }
    }

    @Override
    public MetaInformation getMetaInformation(Object root, Iterable resources, RequestParams requestParams, Serializable castedResourceId) {
        RootMetaDataInformation metaData = null;
        Map<String, Object> selectParams = new HashMap<>();
        PreEmsClient preEmsClient = new PreEmsClient(preEmsConfig);

        selectParams.put("country", getCountry(requestParams).getCountryCode());

        switch ((String) castedResourceId) {
            case "top-box-office":
                selectParams = SqlParameterUtils.setTopBoxOfficeParams(selectParams);
                metaData = (RootMetaDataInformation) preEmsClient.callPreEmsEntity(selectParams, "top-box-office", "meta", RootMetaDataInformation.class);
                if (metaData.totalCount == 0) {
                    LocalDate now = getTodayPST();

                    //exclusive, so if today == Sunday, return last week (so it flips on Monday)
                    LocalDate mostRecentSunday = now.with(previous(DayOfWeek.SUNDAY));
                    selectParams.put("startDate", mostRecentSunday);
                    metaData = (RootMetaDataInformation) preEmsClient.callPreEmsEntity(selectParams, "top-box-office", "fallback/meta", RootMetaDataInformation.class);
                }
                metaData.setRequestParams(requestParams);
                break;

            case "upcoming":
                selectParams = SqlParameterUtils.setUpcomingParams(selectParams);
                metaData = (RootMetaDataInformation) preEmsClient.callPreEmsEntity(selectParams, "upcoming", "meta", RootMetaDataInformation.class);
                metaData.setRequestParams(requestParams);
                break;

            case "opening":
                selectParams = SqlParameterUtils.setOpeningParams(selectParams);
                metaData = (RootMetaDataInformation) preEmsClient.callPreEmsEntity(selectParams, "opening", "meta", RootMetaDataInformation.class);
                metaData.setRequestParams(requestParams);
                break;

            case "top-rentals":
                selectParams = SqlParameterUtils.setTopRentalsParams(selectParams);
                metaData = (RootMetaDataInformation) preEmsClient.callPreEmsEntity(selectParams, "top-rentals", "meta", RootMetaDataInformation.class);
                metaData.setRequestParams(requestParams);
                break;

            case "upcoming-dvds":
                selectParams = SqlParameterUtils.setUpcomingDvdsParam(selectParams);
                metaData = (RootMetaDataInformation) preEmsClient.callPreEmsEntity(selectParams, "upcoming-dvds", "meta", RootMetaDataInformation.class);
                metaData.setRequestParams(requestParams);
                break;

            case "new-on-dvd":
                selectParams = SqlParameterUtils.setNewOnDvdParams(selectParams);
                metaData = (RootMetaDataInformation) preEmsClient.callPreEmsEntity(selectParams, "new-on-dvd", "meta", RootMetaDataInformation.class);
                metaData.setRequestParams(requestParams);

        }
        return metaData;
    }
}
