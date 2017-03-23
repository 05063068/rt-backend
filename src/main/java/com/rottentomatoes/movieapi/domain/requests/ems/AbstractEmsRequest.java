/**
 * Flixster Inc. Copyright (c) 2017. All Rights Reserved.
 */

package com.rottentomatoes.movieapi.domain.requests.ems;

import java.util.Map;

import javax.annotation.Nullable;

import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;

import com.rottentomatoes.movieapi.domain.requests.AbstractJsonRequest;

/**
 * Base class for EMS requests
 * 
 * @author harry
 */
public abstract class AbstractEmsRequest extends AbstractJsonRequest {

    private static final String BASE_URL_PROPERTY = "datasource.perry-ems.url";
    private static final String AUTHENTICATION_HEADER_PROPERTY = "datasource.perry-ems.auth";

    protected AbstractEmsRequest(final Environment environment, final HttpMethod httpMethod,
            final String relativeUrl, @Nullable final Map<String, String> queryParameters,
            @Nullable final Map<String, String> httpHeaders, @Nullable final String apiStatName) {

        super(httpMethod, prepareAbsoluteUrl(environment, relativeUrl), queryParameters,
                prepareHttpHeaders(environment, httpHeaders), apiStatName);
    }

    private static String prepareAbsoluteUrl(final Environment environment,
            final String relativeUrl) {
        return environment.getProperty(BASE_URL_PROPERTY) + relativeUrl;
    }

    private static Map<String, String> prepareHttpHeaders(final Environment environment,
            @Nullable final Map<String, String> httpHeaders) {

        final Map<String, String> authenticationHttpHeader = prepareAuthenticationHttpHeader(
                environment.getProperty(AUTHENTICATION_HEADER_PROPERTY));

        if (httpHeaders == null) return authenticationHttpHeader;
        httpHeaders.putAll(authenticationHttpHeader);
        return httpHeaders;
    }
}
