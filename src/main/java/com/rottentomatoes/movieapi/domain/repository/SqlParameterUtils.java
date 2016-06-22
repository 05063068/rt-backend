package com.rottentomatoes.movieapi.domain.repository;

import static java.time.temporal.TemporalAdjusters.next;
import static java.time.temporal.TemporalAdjusters.nextOrSame;
import static java.time.temporal.TemporalAdjusters.previousOrSame;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Map;

public class SqlParameterUtils {
    private static DayOfWeek[] weekendDays = {DayOfWeek.FRIDAY, DayOfWeek.SATURDAY, DayOfWeek.SUNDAY};

    static Map<String, Object>  setTopBoxOfficeParams(Map<String, Object> selectParams) {
        LocalDate now = LocalDate.now();
        LocalDate start = now.with(previousOrSame(DayOfWeek.FRIDAY));
        /*
          Make adjustments to make the data searched match our feed
          delivery schedule (Tues-Sun use previous Friday's boxoffice actuals,
          Mon use boxoffice estimates)
        */
        if (Arrays.asList(weekendDays).contains(now.getDayOfWeek())) {
            start = start.minusDays(7);
        }
        selectParams.put("startDate", start);

        return selectParams;
    }

    static Map<String, Object> setUpcomingParams(Map<String, Object> selectParams) {
        LocalDate now = LocalDate.now();
        LocalDate start = now.with(next(DayOfWeek.MONDAY));
        LocalDate end = start.plusMonths(3);

        selectParams.put("startDate", start);
        selectParams.put("endDate", end);

        return selectParams;
    }

    static Map<String, Object> setOpeningParams(Map<String, Object> selectParams) {
        LocalDate now = LocalDate.now();
        LocalDate start = now.with(previousOrSame(DayOfWeek.MONDAY));
        LocalDate end = now.with(nextOrSame(DayOfWeek.SUNDAY));

        selectParams.put("startDate", start);
        selectParams.put("endDate", end);

        return selectParams;
    }

    static Map<String, Object> setTopRentalsParams(Map<String, Object> selectParams) {
        LocalDate now = LocalDate.now();
        LocalDate end = now.with(next(DayOfWeek.SUNDAY));
        //Start is 10 weeks in the past.
        LocalDate start = now.minusWeeks(2);

        selectParams.put("startDate", start);
        selectParams.put("endDate", end);

        return selectParams;
    }

    static Map<String, Object> setNewOnDvdParams(Map<String, Object> selectParams) {
        LocalDate now = LocalDate.now();
        LocalDate start = now.minusMonths(2);
        selectParams.put("startDate", start);
        selectParams.put("endDate", now);

        return selectParams;
    }


}
