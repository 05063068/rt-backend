package com.rottentomatoes.movieapi.domain.repository;

import static java.time.temporal.TemporalAdjusters.next;
import static java.time.temporal.TemporalAdjusters.nextOrSame;
import static java.time.temporal.TemporalAdjusters.previousOrSame;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.rottentomatoes.movieapi.domain.model.Movie;
import com.rottentomatoes.movieapi.domain.model.MovieList;

import io.katharsis.queryParams.RequestParams;
import io.katharsis.repository.RelationshipRepository;
import io.katharsis.resource.exception.ResourceNotFoundException;

@Component
public class MovieListToMovieRepository extends AbstractRepository implements RelationshipRepository<MovieList, String, Movie, String> {

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

                return sqlSession.selectList("com.rottentomatoes.movieapi.mappers.MovieListMapper.selectEstimatedTopBoxOfficeMovies", selectParams);

            case "top-box-office":
                now = LocalDate.now();
                start = now.with(previousOrSame(DayOfWeek.FRIDAY));
                selectParams.put("startDate", start);

                List<Movie> retval = sqlSession.selectList("com.rottentomatoes.movieapi.mappers.MovieListMapper.selectTopBoxOfficeMovies", selectParams);
                System.out.println("size of returned list:" + retval.size());
                return retval;
                
            case "upcoming":
                now = LocalDate.now();
                end = now.plusMonths(3);

                selectParams.put("startDate", now);
                selectParams.put("endDate", end);

                return sqlSession.selectList("com.rottentomatoes.movieapi.mappers.MovieListMapper.selectUpcomingMovies", selectParams);

            case "opening":
                now = LocalDate.now();
                start = now.with(previousOrSame(DayOfWeek.MONDAY));
                end = now.with(nextOrSame(DayOfWeek.SUNDAY));

                selectParams.put("startDate", start);
                selectParams.put("endDate", end);

                return sqlSession.selectList("com.rottentomatoes.movieapi.mappers.MovieListMapper.selectOpeningMovies", selectParams);

            case "top-rentals":
                now = LocalDate.now();

                end = now.with(next(DayOfWeek.SUNDAY));
                //Start is 10 weeks in the past.
                start = now.minusWeeks(10);

                selectParams.put("startDate", start);
                selectParams.put("endDate", end);

                return sqlSession.selectList("com.rottentomatoes.movieapi.mappers.MovieListMapper.selectTopRentalMovies", selectParams);

            case "new-on-dvd":
                // TODO: Need to implement this
                now = LocalDate.now();

                end = now.with(next(DayOfWeek.SUNDAY));
                //Start is 10 weeks in the past.
                start = now.minusWeeks(10);

                selectParams.put("startDate", start);
                selectParams.put("endDate", end);

                return sqlSession.selectList("com.rottentomatoes.movieapi.mappers.MovieListMapper.selectTopRentalMovies", selectParams);


            default:
                throw new ResourceNotFoundException("Invalid list type");
        }
    }
}
