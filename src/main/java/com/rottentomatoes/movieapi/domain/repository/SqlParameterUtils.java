package com.rottentomatoes.movieapi.domain.repository;

import static java.time.temporal.TemporalAdjusters.previousOrSame;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Map;

public class SqlParameterUtils {
    private static DayOfWeek[] weekendDays = {DayOfWeek.FRIDAY, DayOfWeek.SATURDAY, DayOfWeek.SUNDAY};

    static void setBoxOfficeParams(Map<String, Object> selectParams) {
        LocalDate now;
        LocalDate start;

        now = LocalDate.now();
        start = now.with(previousOrSame(DayOfWeek.FRIDAY));
        /*
          Make adjustments to make the data searched match our feed
          delivery schedule (Tues-Sun use previous Friday's boxoffice actuals,
          Mon use boxoffice estimates)
        */
        if (Arrays.asList(weekendDays).contains(now.getDayOfWeek())) {
            start = start.minusDays(7);
        }
        selectParams.put("startDate", start);
        selectParams.put("estimated", (now.getDayOfWeek().equals(DayOfWeek.MONDAY)) ? 1 : 0);
    }


}
