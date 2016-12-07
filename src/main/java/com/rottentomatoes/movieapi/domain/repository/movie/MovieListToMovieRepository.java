package com.rottentomatoes.movieapi.domain.repository.movie;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.type.TypeFactory;
import com.rottentomatoes.movieapi.domain.repository.AbstractRepository;
import com.rottentomatoes.movieapi.domain.ems.EmsClient;
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
    
    private Iterable<Movie> hydrateIdList(EmsClient emsClient, EmsClient emsHydrationClient, Map<String, Object> selectParams) {
        // get Ids from (tv)ems endpoint
        List<Integer> movieIds = (List<Integer>) emsClient.callEmsList(selectParams, "movie-list", "top", TypeFactory.defaultInstance().constructCollectionType(List.class, Integer.class));
        if (movieIds != null && movieIds.size() > 0) {
            List<String> idList = movieIds.stream()
                    .map(elt -> elt.toString())
                    .collect(Collectors.toList());
            selectParams.put("ids", String.join(",", idList));
            // hydrate list using pre-ems endpoint
            List movies = (List<Movie>) emsHydrationClient.callEmsList(selectParams, "movie", null, TypeFactory.defaultInstance().constructCollectionType(List.class, Movie.class));

            // Assure the movie order is returned to intended order which is set by the movie id list.
            Collections.sort(movies,
                    Comparator.comparing(item -> movieIds.indexOf(Integer.parseInt(((Movie)item).getId()))));

            return movies;
        }
        return null;

    }

    @Override
    public Iterable<Movie> findManyTargets(String listId, String fieldName, RequestParams requestParams) {
        Map<String, Object> selectParams = new HashMap<>();
        RepositoryUtils.setMovieParams(selectParams, requestParams);
        EmsClient emsClient = emsRouter.fetchEmsClientForPath(listId);
        EmsClient emsHydrationClient = emsRouter.fetchEmsClientForEndpoint(MovieRepository.class);

        selectParams.put("limit", RepositoryUtils.getLimit(fieldName, requestParams));
        selectParams.put("offset", RepositoryUtils.getOffset(fieldName, requestParams));
        selectParams.put("country", RepositoryUtils.getCountry(requestParams).getCountryCode());

        switch (listId) {
            case "top-box-office":
                selectParams = SqlParameterUtils.setTopBoxOfficeParams(selectParams);
                List<Movie> movies = (List<Movie>) emsClient.callEmsList(selectParams, listId, null, TypeFactory.defaultInstance().constructCollectionType(List.class, Movie.class));
                if (movies.isEmpty()) {
                    movies = (List<Movie>) emsClient.callEmsList(selectParams, listId, "fallback", TypeFactory.defaultInstance().constructCollectionType(List.class, Movie.class));
                }
                return movies;
            case "expand-list":
                List<Movie> expandedMovies = (List<Movie>) emsClient.callEmsList(selectParams, listId, null, TypeFactory.defaultInstance().constructCollectionType(List.class, Movie.class));
                return expandedMovies;      

            case "upcoming":
                selectParams = SqlParameterUtils.setUpcomingParams(selectParams);
                return (List<Movie>) emsClient.callEmsList(selectParams, listId, null, TypeFactory.defaultInstance().constructCollectionType(List.class, Movie.class));

            case "opening":
                selectParams = SqlParameterUtils.setOpeningParams(selectParams);
                return (List<Movie>) emsClient.callEmsList(selectParams, listId, null, TypeFactory.defaultInstance().constructCollectionType(List.class, Movie.class));

            case "top-rentals":
                selectParams = SqlParameterUtils.setTopRentalsParams(selectParams);
                return (List<Movie>) emsClient.callEmsList(selectParams, listId, null, TypeFactory.defaultInstance().constructCollectionType(List.class, Movie.class));

            case "upcoming-dvds":
                selectParams = SqlParameterUtils.setUpcomingDvdsParam(selectParams);
                return (List<Movie>) emsClient.callEmsList(selectParams, listId, null, TypeFactory.defaultInstance().constructCollectionType(List.class, Movie.class));

            case "new-on-dvd":
                selectParams = SqlParameterUtils.setNewOnDvdParams(selectParams);
                return (List<Movie>) emsClient.callEmsList(selectParams, listId, null, TypeFactory.defaultInstance().constructCollectionType(List.class, Movie.class));
                
            case "top-for-year":
                selectParams = SqlParameterUtils.setTopForYearParams(selectParams, requestParams);
                return hydrateIdList(emsClient, emsHydrationClient, selectParams);
            case "top-for-theater":
                // Pass the epoch seconds for the most recent Theater release date (nearest Friday) dates within 90 days of this are "in theaters"
                Long inTheaterDateTime = SqlParameterUtils.getMostRecentFriday().toEpochDay() * (24*60*60);
                selectParams.put("in-theater-date", inTheaterDateTime);
                return hydrateIdList(emsClient, emsHydrationClient, selectParams);
            case "top-for-dvd":
                // Pass the epoch seconds for the most recent Theater release date (nearest Friday) dates earlier than this are "on DVD"
                Long onDvdDateTime = SqlParameterUtils.getMostRecentFriday().toEpochDay() * (24*60*60);
                selectParams.put("on-dvd-date", onDvdDateTime);
                return hydrateIdList(emsClient, emsHydrationClient, selectParams);
            case "top-for-genre":
                selectParams = SqlParameterUtils.setTopForGenreParams(selectParams, requestParams);
                return hydrateIdList(emsClient, emsHydrationClient, selectParams);
            case "top-ever":
                return hydrateIdList(emsClient, emsHydrationClient, selectParams);
            default:
                throw new ResourceNotFoundException("Invalid list type");
        }
    }

    @Override
    public MetaInformation getMetaInformation(Object root, Iterable resources, RequestParams requestParams, Serializable castedResourceId) {
        RootMetaDataInformation metaData = null;
        Map<String, Object> selectParams = new HashMap<>();
        EmsClient emsClient;

        selectParams.put("country", RepositoryUtils.getCountry(requestParams).getCountryCode());

        switch ((String) castedResourceId) {
            case "top-box-office":
                emsClient = emsRouter.fetchEmsClientForEndpoint(this.getClass());
                selectParams = SqlParameterUtils.setTopBoxOfficeParams(selectParams);
                metaData = (RootMetaDataInformation) emsClient.callEmsEntity(selectParams, "top-box-office", "meta", RootMetaDataInformation.class);
                if (metaData.totalCount == 0) {
                    LocalDate now = getTodayPST();

                    //exclusive, so if today == Sunday, return last week (so it flips on Monday)
                    LocalDate mostRecentSunday = now.with(previous(DayOfWeek.SUNDAY));
                    selectParams.put("startDate", mostRecentSunday);
                    metaData = (RootMetaDataInformation) emsClient.callEmsEntity(selectParams, "top-box-office", "fallback/meta", RootMetaDataInformation.class);
                }
                metaData.setRequestParams(requestParams);
                break;

            case "upcoming":
                emsClient = emsRouter.fetchEmsClientForEndpoint(this.getClass());
                selectParams = SqlParameterUtils.setUpcomingParams(selectParams);
                metaData = (RootMetaDataInformation) emsClient.callEmsEntity(selectParams, "upcoming", "meta", RootMetaDataInformation.class);
                metaData.setRequestParams(requestParams);
                break;

            case "opening":
                emsClient = emsRouter.fetchEmsClientForEndpoint(this.getClass());
                selectParams = SqlParameterUtils.setOpeningParams(selectParams);
                metaData = (RootMetaDataInformation) emsClient.callEmsEntity(selectParams, "opening", "meta", RootMetaDataInformation.class);
                metaData.setRequestParams(requestParams);
                break;

            case "top-rentals":
                emsClient = emsRouter.fetchEmsClientForEndpoint(this.getClass());
                selectParams = SqlParameterUtils.setTopRentalsParams(selectParams);
                metaData = (RootMetaDataInformation) emsClient.callEmsEntity(selectParams, "top-rentals", "meta", RootMetaDataInformation.class);
                metaData.setRequestParams(requestParams);
                break;

            case "upcoming-dvds":
                emsClient = emsRouter.fetchEmsClientForEndpoint(this.getClass());
                selectParams = SqlParameterUtils.setUpcomingDvdsParam(selectParams);
                metaData = (RootMetaDataInformation) emsClient.callEmsEntity(selectParams, "upcoming-dvds", "meta", RootMetaDataInformation.class);
                metaData.setRequestParams(requestParams);
                break;

            case "new-on-dvd":
                emsClient = emsRouter.fetchEmsClientForEndpoint(this.getClass());
                selectParams = SqlParameterUtils.setNewOnDvdParams(selectParams);
                metaData = (RootMetaDataInformation) emsClient.callEmsEntity(selectParams, "new-on-dvd", "meta", RootMetaDataInformation.class);
                metaData.setRequestParams(requestParams);

        }
        return metaData;
    }
}
