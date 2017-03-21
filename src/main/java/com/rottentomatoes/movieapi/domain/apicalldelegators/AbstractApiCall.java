/**
 * Flixster Inc. Copyright (c) 2017. All Rights Reserved.
 */

package com.rottentomatoes.movieapi.domain.apicalldelegators;

import org.springframework.core.env.Environment;

import io.katharsis.queryParams.RequestParams;

/**
 * Represents an abstract API implementation for a client facade endpoint
 * 
 * @author harry
 */
public abstract class AbstractApiCall {

    protected Environment environment;
    protected String fieldName;
    protected RequestParams requestParams;

    protected AbstractApiCall(final Environment environment, final String fieldName, final RequestParams requestParams) {
        this.environment = environment;
        this.fieldName = fieldName;
        this.requestParams = requestParams;
    }

    /**
     * Makes the required service API calls to resolve a client facade request
     */
    public abstract <T> T process();
}
