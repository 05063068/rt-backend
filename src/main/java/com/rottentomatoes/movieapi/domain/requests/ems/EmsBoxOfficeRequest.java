/**
 * Flixster Inc. Copyright (c) 2017. All Rights Reserved.
 */

package com.rottentomatoes.movieapi.domain.requests.ems;

import java.util.HashMap;
import java.util.Map;

import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;

import com.rottentomatoes.movieapi.utils.RepositoryUtils;

import io.katharsis.queryParams.RequestParams;

public class EmsBoxOfficeRequest extends AbstractEmsRequest {
    public static final String API_STAT_NAME = "EMS_BOX_OFFICE";

    private static final String RELATIVE_URL = "/v1/boxoffice";

    public EmsBoxOfficeRequest(final Environment environment, final String fieldName,
            final RequestParams requestParams) {
        super(environment, HttpMethod.GET, RELATIVE_URL,
                prepareQueryParams(fieldName, requestParams), null, API_STAT_NAME);
    }

    private static Map<String, String> prepareQueryParams(final String fieldName,
            final RequestParams requestParams) {
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("limit", RepositoryUtils.getLimit(fieldName, requestParams).toString());
        queryParams.put("country", RepositoryUtils.getCountry(requestParams).getCountryCode());
        return queryParams;
    }
}
