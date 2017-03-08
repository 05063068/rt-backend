/**
 * Flixster Inc. Copyright (c) 2017. All Rights Reserved.
 */

package com.rottentomatoes.movieapi.domain.model.apicalldelegators;

import java.util.Map;

import org.springframework.core.env.Environment;

/**
 * Represents an abstract API implementation for a client facade endpoint
 * 
 * @author harry
 */
public abstract class AbstractApiCall {

    protected Environment environment;
    protected Map<String, String> queryParams;

    protected AbstractApiCall(final Environment environment,
            final Map<String, String> queryParams) {
        this.environment = environment;
        this.queryParams = queryParams;
    }

    /**
     * Makes the required service API calls to resolve a client facade request
     */
    public abstract <T> T process();
}
