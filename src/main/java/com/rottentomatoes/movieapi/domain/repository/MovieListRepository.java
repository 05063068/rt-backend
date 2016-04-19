package com.rottentomatoes.movieapi.domain.repository;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.time.temporal.TemporalAdjusters.*;

import com.rottentomatoes.movieapi.domain.meta.RootMetaDataInformation;
import io.katharsis.queryParams.PaginationKeys;
import io.katharsis.repository.MetaRepository;
import io.katharsis.response.MetaInformation;
import org.springframework.stereotype.Component;

import com.rottentomatoes.movieapi.domain.model.Movie;
import com.rottentomatoes.movieapi.domain.model.MovieList;

import io.katharsis.queryParams.RequestParams;
import io.katharsis.repository.ResourceRepository;
import io.katharsis.resource.exception.ResourceNotFoundException;

@Component
public class MovieListRepository extends AbstractRepository implements ResourceRepository<MovieList, String>, MetaRepository {

    @Override
    public <S extends MovieList> S save(S entity) {
        return null;
    }

    @Override
    public void delete(String aLong) {
    }

    @Override
    public MovieList findOne(String listId, RequestParams requestParams) {
        MovieList list = new MovieList();
        Map<String, Object> selectParams = new HashMap<>();
        List<Movie> movies;

        LocalDate now;
        LocalDate start;
        LocalDate end;

        selectParams.put("limit", getLimit("", requestParams));
        selectParams.put("offset", getOffset("", requestParams));
        selectParams.put("country", "us");

        switch (listId) {
            case "top-box-office-estimated":
                now = LocalDate.now();
                start = now.with(previousOrSame(DayOfWeek.FRIDAY));
                selectParams.put("startDate", start);

                movies = sqlSession.selectList("com.rottentomatoes.movieapi.mappers.MovieListMapper.selectEstimatedTopBoxOfficeMovies", selectParams);

                list.setId(listId);
                list.setMovies(movies);
                return list;

            case "top-box-office":
                now = LocalDate.now();
                start = now.with(previousOrSame(DayOfWeek.FRIDAY));
                selectParams.put("startDate", start);

                movies = sqlSession.selectList("com.rottentomatoes.movieapi.mappers.MovieListMapper.selectTopBoxOfficeMovies", selectParams);

                list.setId(listId);
                list.setMovies(movies);
                return list;

            case "upcoming":
                now = LocalDate.now();
                end = now.plusMonths(3);

                selectParams.put("startDate", now);
                selectParams.put("endDate", end);

                movies = sqlSession.selectList("com.rottentomatoes.movieapi.mappers.MovieListMapper.selectUpcomingMovies", selectParams);

                list.setId(listId);
                list.setMovies(movies);
                return list;

            case "opening":
                now = LocalDate.now();
                start = now.with(previousOrSame(DayOfWeek.MONDAY));
                end = now.with(nextOrSame(DayOfWeek.SUNDAY));

                selectParams.put("startDate", start);
                selectParams.put("endDate", end);

                movies = sqlSession.selectList("com.rottentomatoes.movieapi.mappers.MovieListMapper.selectOpeningMovies", selectParams);

                list.setId(listId);
                list.setMovies(movies);
                return list;

            case "top-rentals":
                now = LocalDate.now();

                end = now.with(next(DayOfWeek.SUNDAY));
                //Start is 10 weeks in the past.
                start = now.minusWeeks(10);

                selectParams.put("startDate", start);
                selectParams.put("endDate", end);

                movies = sqlSession.selectList("com.rottentomatoes.movieapi.mappers.MovieListMapper.selectTopRentalMovies", selectParams);

                list.setId(listId);
                list.setMovies(movies);
                return list;
                
            case "new-on-dvd":
            	// TODO: Need to implement this
            	now = LocalDate.now();

                end = now.with(next(DayOfWeek.SUNDAY));
                //Start is 10 weeks in the past.
                start = now.minusWeeks(10);

                selectParams.put("startDate", start);
                selectParams.put("endDate", end);

                movies = sqlSession.selectList("com.rottentomatoes.movieapi.mappers.MovieListMapper.selectTopRentalMovies", selectParams);

                list.setId(listId);
                list.setMovies(movies);
                return list;

            default:
                throw new ResourceNotFoundException("Invalid list type");
        }
    }

    @Override
    public Iterable<MovieList> findAll(RequestParams requestParams) {
        return null;
    }

    @Override
    public Iterable<MovieList> findAll(Iterable<String> ids, RequestParams requestParams) {
        return null;
    }

    @Override
    public MetaInformation getMetaInformation(Object root, Iterable resources, RequestParams requestParams, Serializable castedResourceId) {
        LocalDate now;
        LocalDate start;
        LocalDate end;
        RootMetaDataInformation metaData = null;

        Map<String, Object> selectParams = new HashMap<>();
        selectParams.put("country", "us");

        switch ((String) castedResourceId) {
            case "top-box-office-estimated":
                now = LocalDate.now();
                start = now.with(previousOrSame(DayOfWeek.FRIDAY));

                selectParams.put("startDate", start);

                metaData = sqlSession.selectOne("com.rottentomatoes.movieapi.mappers.MovieListMapper.selectEstimatedTopBoxOfficeMoviesCount", selectParams);
                metaData.setRequestParams(requestParams);
                return metaData;

            case "top-box-office":
                now = LocalDate.now();
                start = now.with(previousOrSame(DayOfWeek.FRIDAY));

                selectParams.put("startDate", start);

                metaData = sqlSession.selectOne("com.rottentomatoes.movieapi.mappers.MovieListMapper.selectTopBoxOfficeMoviesCount", selectParams);
                metaData.setRequestParams(requestParams);
                return metaData;

            case "upcoming":
                now = LocalDate.now();
                end = now.plusMonths(3);

                selectParams.put("startDate", now);
                selectParams.put("endDate", end);

                metaData = sqlSession.selectOne("com.rottentomatoes.movieapi.mappers.MovieListMapper.selectUpcomingMoviesCount", selectParams);
                metaData.setRequestParams(requestParams);
                return metaData;

            case "opening":
                now = LocalDate.now();
                start = now.with(previousOrSame(DayOfWeek.MONDAY));
                end = now.with(nextOrSame(DayOfWeek.SUNDAY));

                selectParams.put("startDate", start);
                selectParams.put("endDate", end);

                metaData = sqlSession.selectOne("com.rottentomatoes.movieapi.mappers.MovieListMapper.selectOpeningMoviesCount", selectParams);
                metaData.setRequestParams(requestParams);
                return metaData;

            case "top-rentals":
                now = LocalDate.now();
                end = now.with(next(DayOfWeek.SUNDAY));
                //Start is 10 weeks in the past.
                start = now.minusWeeks(10);

                selectParams.put("startDate", start);
                selectParams.put("endDate", end);

                metaData = sqlSession.selectOne("com.rottentomatoes.movieapi.mappers.MovieListMapper.selectTopRentalMoviesCount", selectParams);
                metaData.setRequestParams(requestParams);
                return metaData;
        }
        metaData.setRequestParams(requestParams);
        return metaData;
    }
}
