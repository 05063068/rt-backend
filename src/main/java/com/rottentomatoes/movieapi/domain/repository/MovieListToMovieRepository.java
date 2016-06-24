package com.rottentomatoes.movieapi.domain.repository;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.rottentomatoes.movieapi.domain.meta.RootMetaDataInformation;
import com.rottentomatoes.movieapi.domain.model.Movie;
import com.rottentomatoes.movieapi.domain.model.MovieList;

import io.katharsis.queryParams.RequestParams;
import io.katharsis.repository.MetaRepository;
import io.katharsis.repository.RelationshipRepository;
import io.katharsis.resource.exception.ResourceNotFoundException;
import io.katharsis.response.MetaInformation;

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
        MovieRepository.setMovieParams(selectParams, requestParams);

        selectParams.put("limit", getLimit("", requestParams));
        selectParams.put("offset", getOffset("", requestParams));
        selectParams.put("country", getCountry(requestParams).getCountryCode());

        switch (listId) {
            case "top-box-office":
                selectParams = SqlParameterUtils.setTopBoxOfficeParams(selectParams);
                List<Movie> movies = sqlSession.selectList("com.rottentomatoes.movieapi.mappers.MovieListMapper.selectTopBoxOfficeMovies", selectParams);
                if (movies.isEmpty()) {
                    movies = sqlSession.selectList("com.rottentomatoes.movieapi.mappers.MovieListMapper.selectTopBoxOfficeMoviesFallback", selectParams);
                }
                return movies;

            case "upcoming":
                return sqlSession.selectList("com.rottentomatoes.movieapi.mappers.MovieListMapper.selectUpcomingMovies", SqlParameterUtils.setUpcomingParams(selectParams));

            case "opening":
                return sqlSession.selectList("com.rottentomatoes.movieapi.mappers.MovieListMapper.selectOpeningMovies", SqlParameterUtils.setOpeningParams(selectParams));

            case "top-rentals":
                return sqlSession.selectList("com.rottentomatoes.movieapi.mappers.MovieListMapper.selectTopRentalMovies", SqlParameterUtils.setTopRentalsParams(selectParams));

            case "new-on-dvd":
                return sqlSession.selectList("com.rottentomatoes.movieapi.mappers.MovieListMapper.selectNewDvd", SqlParameterUtils.setNewOnDvdParams(selectParams));

            default:
                throw new ResourceNotFoundException("Invalid list type");
        }
    }

    @Override
    public MetaInformation getMetaInformation(Object root, Iterable resources, RequestParams requestParams, Serializable castedResourceId) {
        RootMetaDataInformation metaData = null;
        Map<String, Object> selectParams = new HashMap<>();

        selectParams.put("country", getCountry(requestParams).getCountryCode());

        switch ((String) castedResourceId) {
            case "top-box-office":
                selectParams = SqlParameterUtils.setTopBoxOfficeParams(selectParams);
                metaData = sqlSession.selectOne("com.rottentomatoes.movieapi.mappers.MovieListMapper.selectTopBoxOfficeMoviesCount", selectParams);
                if (metaData.totalCount == 0) {
                    LocalDate now = LocalDate.now();

                    //exclusive, so if today == Sunday, return last week (so it flips on Monday)
                    LocalDate mostRecentSunday = now.with(previous(DayOfWeek.SUNDAY));
                    selectParams.put("startDate", mostRecentSunday);
                    metaData = sqlSession.selectOne("com.rottentomatoes.movieapi.mappers.MovieListMapper.selectTopBoxOfficeMoviesFallbackCount", selectParams);
                }
                metaData.setRequestParams(requestParams);
                break;

            case "upcoming":
                metaData = sqlSession.selectOne("com.rottentomatoes.movieapi.mappers.MovieListMapper.selectUpcomingMoviesCount", SqlParameterUtils.setUpcomingParams(selectParams));
                metaData.setRequestParams(requestParams);
                break;

            case "opening":
                metaData = sqlSession.selectOne("com.rottentomatoes.movieapi.mappers.MovieListMapper.selectOpeningMoviesCount", SqlParameterUtils.setOpeningParams(selectParams));
                metaData.setRequestParams(requestParams);
                break;

            case "top-rentals":
                metaData = sqlSession.selectOne("com.rottentomatoes.movieapi.mappers.MovieListMapper.selectTopRentalMoviesCount", SqlParameterUtils.setTopRentalsParams(selectParams));
                metaData.setRequestParams(requestParams);
                break;

            case "new-on-dvd":
                metaData = sqlSession.selectOne("com.rottentomatoes.movieapi.mappers.MovieListMapper.selectNewDvdCount", SqlParameterUtils.setNewOnDvdParams(selectParams));
                metaData.setRequestParams(requestParams);

        }
        return metaData;
    }
}
