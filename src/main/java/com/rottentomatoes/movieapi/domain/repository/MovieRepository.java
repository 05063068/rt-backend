/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.rottentomatoes.movieapi.domain.repository;

import static com.rottentomatoes.movieapi.domain.repository.SqlParameterUtils.getMostRecentFriday;
import static com.rottentomatoes.movieapi.domain.repository.SqlParameterUtils.getTodayPST;
import static java.time.temporal.TemporalAdjusters.previousOrSame;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import com.rottentomatoes.movieapi.domain.meta.RootMetaDataInformation;

import io.katharsis.repository.MetaRepository;
import io.katharsis.response.MetaInformation;

import org.springframework.stereotype.Component;

import com.rottentomatoes.movieapi.domain.model.Movie;

import io.katharsis.queryParams.RequestParams;
import io.katharsis.repository.ResourceRepository;

@SuppressWarnings("rawtypes")
@Component
public class MovieRepository extends AbstractRepository implements ResourceRepository<Movie, String>, MetaRepository {

    @Override
    public <S extends Movie> S save(S entity) {
        return null;
    }

    @Override
    public void delete(String aLong) {
    }

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
     * @param selectParams - parameters to be passed to Mybatis queries (modified by this method).
     * @param requestParams - parameters from the HTTP request
     */
    static public void setMovieParams(Map<String, Object> selectParams, RequestParams requestParams) {
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

    @Override
    public Movie findOne(String movieId, RequestParams requestParams) {
        Map<String, Object> selectParams = new HashMap<>();
        selectParams.put("id", movieId);
        setMovieParams(selectParams, requestParams);
        Movie movie = sqlSession.selectOne("com.rottentomatoes.movieapi.mappers.MovieMapper.selectMovieById", selectParams);
        return movie;
    }

    @Override
    public Iterable<Movie> findAll(RequestParams requestParams) {
        return null;
    }

    @Override
    public Iterable<Movie> findAll(Iterable<String> ids, RequestParams requestParams) {
        return null;
    }

    @Override
    public MetaInformation getMetaInformation(Object root, Iterable resources, RequestParams requestParams, Serializable castedResourceId) {
        RootMetaDataInformation metaData = new RootMetaDataInformation();
        metaData.setRequestParams(requestParams);
        return metaData;
    }
}
