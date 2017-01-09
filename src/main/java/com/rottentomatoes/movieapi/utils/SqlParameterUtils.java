package com.rottentomatoes.movieapi.utils;

import io.katharsis.queryParams.RequestParams;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import static java.time.temporal.TemporalAdjusters.*;

public class SqlParameterUtils {
    private static DayOfWeek[] weekendDays = {DayOfWeek.FRIDAY, DayOfWeek.SATURDAY, DayOfWeek.SUNDAY};
    private static ZoneId PST_ZONE_ID = ZoneId.of("America/Los_Angeles");

    public static LocalDate getTodayPST() {
        LocalDate nowPst = LocalDate.now(PST_ZONE_ID);
        return nowPst;
    }

    public static LocalDate getMostRecentFriday() {
        LocalDate now = getTodayPST();
        LocalDate mostRecentFriday = now.with(previous(DayOfWeek.FRIDAY));

        // We don't want the box office numbers to switch until Monday (we input data over weekend if we get it)
        // So if today is Saturday or Sunday, go back even 1 more week
        if (now.getDayOfWeek().equals(DayOfWeek.SATURDAY) || now.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
            mostRecentFriday = mostRecentFriday.minusDays(7);
        }
        return mostRecentFriday;
    }
    /**
     * Get the correct date that can be used to find MovieBoxOffice
     *
     * All box office dates are stored keyed to the Friday, so grab most recent Friday.
     * if curDate is a friday, we use last week's friday date
     */
    public static Map<String, Object>  setTopBoxOfficeParams(Map<String, Object> selectParams) {

        selectParams.put("startDate", getMostRecentFriday());
        selectParams.put("endDate", getMostRecentFriday().plusDays(5));

        return selectParams;
    }

    public static Map<String, Object> setUpcomingParams(Map<String, Object> selectParams) {
        LocalDate now = getTodayPST();
        LocalDate start = now.with(next(DayOfWeek.MONDAY));
        LocalDate end = start.plusMonths(3);
        selectParams.put("startDate", start);
        selectParams.put("endDate", end);

        return selectParams;
    }

    public static Map<String, Object> setOpeningParams(Map<String, Object> selectParams) {
        LocalDate now = getTodayPST();
        LocalDate start = now.with(previousOrSame(DayOfWeek.MONDAY));
        LocalDate end = now.with(nextOrSame(DayOfWeek.SUNDAY));
        selectParams.put("startDate", start);
        selectParams.put("endDate", end);

        return selectParams;
    }

    public static Map<String, Object> setTopRentalsParams(Map<String, Object> selectParams) {
        LocalDate now = getTodayPST();
        LocalDate end = now.with(previousOrSame(DayOfWeek.SUNDAY));
        LocalDate start = now.minusMonths(2);
        selectParams.put("startDate", start);
        selectParams.put("endDate", end);

        return selectParams;
    }

    public static Map<String, Object> setUpcomingDvdsParam(Map<String, Object> selectParams) {
        LocalDate now = getTodayPST();
        LocalDate start = now.with(next(DayOfWeek.MONDAY));
        LocalDate end = start.plusMonths(3);
        selectParams.put("startDate", start);
        selectParams.put("endDate", end);

        return selectParams;
    }

    public static Map<String, Object> setNewOnDvdParams(Map<String, Object> selectParams) {
        LocalDate now = getTodayPST();
        LocalDate start = now.with(previousOrSame(DayOfWeek.MONDAY));
        LocalDate end = now.with(next(DayOfWeek.SUNDAY));
        selectParams.put("startDate", start);
        selectParams.put("endDate", end);

        return selectParams;
    }
    
    public static String getBoxofficeWeek(RequestParams requestParams) {
        // set parameters specific to this top list
        if (requestParams.getFilters() != null && requestParams.getFilters().containsKey("box-office-week")) {
            return "weeks/" + (String) requestParams.getFilters().get("box-office-week");
        } else {
            return "";
        }
    }
    
    public static Map<String, Object> setTopForYearParams(Map<String, Object> selectParams, RequestParams requestParams) {
        // set parameters specific to this top list
        if (requestParams.getFilters() != null && requestParams.getFilters().containsKey("year")) {
            selectParams.put("year", requestParams.getFilters().get("year"));
        } else {
            Integer year = SqlParameterUtils.getMostRecentFriday().getYear();
            selectParams.put("year", year);
        }
        return selectParams;
    }
    
    public static Map<String, Object> setTopForGenreParams(Map<String, Object> selectParams, RequestParams requestParams) {
        // set parameters specific to this top list
        if (requestParams.getFilters() != null && requestParams.getFilters().containsKey("genre")) {
            selectParams.put("genre-name", requestParams.getFilters().get("genre"));
        } 
        return selectParams;
    }

}
