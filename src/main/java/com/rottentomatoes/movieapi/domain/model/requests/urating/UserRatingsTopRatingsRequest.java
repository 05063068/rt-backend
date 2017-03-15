/**
 * Flixster Inc. Copyright (c) 2017. All Rights Reserved.
 */

package com.rottentomatoes.movieapi.domain.model.requests.urating;

import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;

public class UserRatingsTopRatingsRequest extends AbstractUserRatingsRequest {
    public static final String API_STAT_NAME = "USER_RATINGS_TOP_RATINGS";

    private static final String RELATIVE_URL_ID_PLACEHOLDER = "{id}";
    private static final String RELATIVE_URL = "/movies/v1/" + RELATIVE_URL_ID_PLACEHOLDER
            + "/ratings/top";

    public UserRatingsTopRatingsRequest(final Environment environment, final String movieId) {
        super(environment, HttpMethod.GET, prepareRelativeUrl(movieId), null, null, API_STAT_NAME);
    }

    private static final String prepareRelativeUrl(String movieId) {
        return RELATIVE_URL.replace(RELATIVE_URL_ID_PLACEHOLDER, movieId);
    }
}
