/**
 * Flixster Inc. Copyright (c) 2017. All Rights Reserved.
 */

package com.rottentomatoes.movieapi.domain.model.requests.urating;

import java.util.Map;

import javax.annotation.Nullable;

import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;

import com.rottentomatoes.movieapi.domain.model.requests.AbstractJsonRequest;

/**
 * Base class for User Ratings requests
 * 
 * @author harry
 */
public abstract class AbstractUserRatingsRequest extends AbstractJsonRequest {

    private static final String BASE_URL_PROPERTY = "datasource.user-ratings.url";

    public AbstractUserRatingsRequest(final Environment environment, final HttpMethod httpMethod,
            final String relativeUrl, @Nullable final Map<String, String> queryParameters,
            @Nullable final Map<String, String> httpHeaders, @Nullable final String apiStatName) {
        super(httpMethod, prepareAbsoluteUrl(environment, relativeUrl), queryParameters,
                httpHeaders, apiStatName);
    }

    private static String prepareAbsoluteUrl(final Environment environment,
            final String relativeUrl) {
        return environment.getProperty(BASE_URL_PROPERTY) + relativeUrl;
    }
}
