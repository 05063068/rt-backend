package com.rottentomatoes.movieapi.utils;

import com.rottentomatoes.movieapi.enums.Country;
import io.katharsis.queryParams.PaginationKeys;
import io.katharsis.queryParams.RequestParams;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Map;

import static com.rottentomatoes.movieapi.utils.SqlParameterUtils.getTodayPST;
import static java.time.temporal.TemporalAdjusters.previousOrSame;


public class RepositoryUtils {

    private static final String LIMIT = "Limit";
    private static final Integer DEFAULT_LIMIT = 10;
    private static final Integer DEFAULT_OFFSET = 0;

    /**
     * setMovieParams sets the parameters used to calculate movie release windows.
     *
     * Theater and Dvd release windows are orthogonal properties, each of which has a threshold
     * for when the release date is near enough to be considered upcoming (currently a long time in
     * advance), when the movie or dvd is newly released (in the current week or somewhat before for
     * dvds), or when the movie is currently available but not new (three months from release for
     * theatrical releases, any time since release for dvds). These buckets of upcoming, new, available
     * and none-of-the-above are mutually exclusive.
     *
     * As has been the case historically, we are untroubled by such details as Dvds going out of print,
     * or movies ending their theater run sooner than three months after release.  And certainly don't
     * consider that the "Rocky Horror Picture Show" is doubtless still playing in some theater,
     * somewhere.  Perhaps, these simplifying assumptions will need to be refined at a future date.
     *
     * The logic for calculating the thresholds is here in the Java code, while the calculation of where
     * a movie's release dates fall within the thresholds is done in SQL.
     *
     * This method is public static so that methods that query for movie lists, and such, rather than direct
     * queries for individual movies, can set
     * the required parameters, if they require valid release window information.  The query does not
     * fail without these parameters.  It just always sets release window properties to "NA".
     *
     * Another possible, future, "boyscouting" activity would be to provide a third set of release windows for
     * availability online.
     *
     * @param selectParams - parameters to be p assed to Mybatis queries (modified by this method).
     * @param requestParams - parameters from the HTTP request
     */
     public static void setMovieParams(Map<String, Object> selectParams, RequestParams requestParams) {
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
        LocalDate boxOfficeStartDate =  SqlParameterUtils.getMostRecentFriday();

         if (requestParams.getFilters().containsKey("videoSource")) {
             selectParams.put("videoSource", requestParams.getFilters().get("videoSource"));
         }
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


    public static Country getCountry(RequestParams requestParams) {
        if (requestParams.getFilters() != null && requestParams.getFilters().get("country") != null) {
            return Country.getCountryEnumFromString((String) requestParams.getFilters().get("country"));
        } else {
            return Country.getDefault();
        }
    }


    public static Integer getLimit(String fieldName, RequestParams requestParams) {
        if (requestParams != null) {
            if (requestParams.getPagination() != null && requestParams.getPagination().containsKey(PaginationKeys.limit)) {
                return requestParams.getPagination().get(PaginationKeys.limit);
            } else if (requestParams.getFilters() != null && requestParams.getFilters().containsKey(fieldName + LIMIT)) {
                return (Integer) requestParams.getFilters().get(fieldName + LIMIT);
            }
        }
        return DEFAULT_LIMIT;
    }

    public static Integer getOffset(String fieldName, RequestParams requestParams) {
        if (requestParams != null) {
            if (requestParams.getPagination() != null && requestParams.getPagination().containsKey(PaginationKeys.offset)) {
                return requestParams.getPagination().get(PaginationKeys.offset);
            }
        }
        return DEFAULT_OFFSET;

    }

}
