package com.rottentomatoes.movieapi.domain.repository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static java.time.temporal.TemporalAdjusters.*;

import org.springframework.stereotype.Component;

import com.rottentomatoes.movieapi.domain.model.Movie;
import com.rottentomatoes.movieapi.domain.model.MovieList;

import io.katharsis.queryParams.RequestParams;
import io.katharsis.repository.ResourceRepository;
import io.katharsis.resource.exception.ResourceNotFoundException;

@Component
public class MovieListRepository extends AbstractRepository implements ResourceRepository<MovieList, String> {

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

        Integer limit = requestParams.getFilters() != null && requestParams.getFilters().get("limit") != null ? (Integer) requestParams.getFilters().get("limit") : null;
        Integer offset = requestParams.getFilters() != null && requestParams.getFilters().get("offset") != null ? (Integer) requestParams.getFilters().get("offset") : null;
        selectParams.put("limit", limit);
        selectParams.put("offset", offset);
        selectParams.put("country", "us");

        switch (listId) {
            case "top-box-office":
                now = LocalDate.now();
                start = now.with(previousOrSame(DayOfWeek.FRIDAY));
                selectParams.put("startDate", start);

                movies = sqlSession.selectList("com.rottentomatoes.movieapi.mappers.MovieListMapper.selectTopBoxOfficeMovies", selectParams);

                // If top box office is empty then return estimates based on theater showtimes.
                if(movies.isEmpty()) {
                    movies = sqlSession.selectList("com.rottentomatoes.movieapi.mappers.MovieListMapper.selectEstimatedTopBoxOfficeMovies", selectParams);
                }

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
        // TODO Auto-generated method stub
        return null;
    }
}
