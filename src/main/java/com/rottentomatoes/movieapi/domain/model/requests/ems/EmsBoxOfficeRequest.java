/**
 * Flixster Inc. Copyright (c) 2017. All Rights Reserved.
 */

package com.rottentomatoes.movieapi.domain.model.requests.ems;

import java.util.Map;

import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;

public class EmsBoxOfficeRequest extends AbstractEmsRequest {
    public static final String API_STAT_NAME = "EMS_BOX_OFFICE";

    private static final String RELATIVE_URL = "/v1/boxoffice";

    public EmsBoxOfficeRequest(final Environment environment,
            final Map<String, String> queryParameters) {
        super(environment, HttpMethod.GET, RELATIVE_URL, queryParameters, null, API_STAT_NAME);
    }
}
