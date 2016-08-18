package com.rottentomatoes.movieapi.domain.repository;

import com.rottentomatoes.movieapi.domain.model.Movie;
import com.rottentomatoes.movieapi.domain.model.MovieSupplementaryInfo;
import io.katharsis.queryParams.RequestParams;
import io.katharsis.repository.RelationshipRepository;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static com.rottentomatoes.movieapi.domain.repository.SqlParameterUtils.getMostRecentFriday;
import static com.rottentomatoes.movieapi.domain.repository.SqlParameterUtils.getTodayPST;
import static java.time.temporal.TemporalAdjusters.previousOrSame;

@SuppressWarnings("rawtypes")
@Component
public class MovieToMovieSupplementaryInfoRepository extends AbstractRepository implements RelationshipRepository<Movie, String, MovieSupplementaryInfo, String> {

    @Override
    public void setRelation(Movie movie, String s, String s2) {

    }

    @Override
    public void setRelations(Movie movie, Iterable<String> iterable, String s) {

    }

    @Override
    public void addRelations(Movie movie, Iterable<String> iterable, String s) {

    }

    @Override
    public void removeRelations(Movie movie, Iterable<String> iterable, String s) {

    }

    @Override
    public MovieSupplementaryInfo findOneTarget(String movieId, String fieldName, RequestParams requestParams) {
        Map<String, Object> selectParams = new HashMap<>();
        setMovieParams(selectParams, requestParams);
        PreEmsClient preEmsClient = new PreEmsClient<MovieSupplementaryInfo>(preEmsConfig);
        MovieSupplementaryInfo movieSupplementaryInfo = (MovieSupplementaryInfo) preEmsClient.callPreEmsEntity(selectParams, "movie", movieId + "/supplementary-info", MovieSupplementaryInfo.class);


        return movieSupplementaryInfo;
    }

    @Override
    public Iterable<MovieSupplementaryInfo> findManyTargets(String s, String s2, RequestParams requestParams) {
        return null;
    }

    private void setMovieParams(Map<String, Object> selectParams, RequestParams requestParams) {
        LocalDate now;
        now = getTodayPST();
        LocalDate startOfWeek = now.with(previousOrSame(DayOfWeek.MONDAY));
        LocalDate endOfWeek = now.plusDays(7);

        LocalDate upcomingDate = now.plusYears(30);
        LocalDate openingDate = endOfWeek;
        LocalDate inTheaterDate = now.minusDays(90);
        LocalDate upcomingDvdDate = endOfWeek.plusYears(30);  // 30 is easier to spell than forever, and practically the same
        LocalDate newDvdDate = endOfWeek.minusDays(10*7 -1);
        LocalDate onDvdDate = endOfWeek.minusYears(30);
        LocalDate boxOfficeStartDate =  getMostRecentFriday();

        selectParams.put("upcomingDate", upcomingDate);
        selectParams.put("boxOfficeStartDate", boxOfficeStartDate);
        selectParams.put("openingDate", openingDate);
        selectParams.put("inTheaterDate", inTheaterDate);
        selectParams.put("upcomingDvdDate", upcomingDvdDate);
        selectParams.put("newDvdDate", newDvdDate);
        selectParams.put("onDvdDate", onDvdDate);
        selectParams.put("country", getCountry(requestParams).getCountryCode());
        SqlParameterUtils.setTopBoxOfficeParams(selectParams);
    }
}
