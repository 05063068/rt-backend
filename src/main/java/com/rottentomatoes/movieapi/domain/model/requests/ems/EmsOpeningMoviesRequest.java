/**
 * Flixster Inc. Copyright (c) 2017. All Rights Reserved.
 */

package com.rottentomatoes.movieapi.domain.model.requests.ems;

import java.util.HashMap;
import java.util.Map;

import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;

import com.rottentomatoes.movieapi.utils.RepositoryUtils;

import io.katharsis.queryParams.RequestParams;

public class EmsOpeningMoviesRequest extends AbstractEmsRequest {
    public static final String API_STAT_NAME = "EMS_OPENING_MOVIES";

    private static final String RELATIVE_URL_COUNTRY_PLACEHOLDER = "{country}";
    private static final String RELATIVE_URL = "/v1/movies/opening/"
            + RELATIVE_URL_COUNTRY_PLACEHOLDER;

    public EmsOpeningMoviesRequest(final Environment environment, final String fieldName,
            final RequestParams requestParams) {
        super(environment, HttpMethod.GET, prepareRelativeUrl(requestParams),
                prepareQueryParams(fieldName, requestParams), null, API_STAT_NAME);
    }

    private static String prepareRelativeUrl(RequestParams requestParams) {
        return RELATIVE_URL.replace(RELATIVE_URL_COUNTRY_PLACEHOLDER,
                RepositoryUtils.getCountry(requestParams).getThreeLetterCountryCode());
    }

    private static Map<String, String> prepareQueryParams(final String fieldName,
            final RequestParams requestParams) {
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("limit", RepositoryUtils.getLimit(fieldName, requestParams).toString());
        queryParams.put("offset", RepositoryUtils.getOffset(fieldName, requestParams).toString());
        return queryParams;
    }
}
