/**
 * Flixster Inc. Copyright (c) 2017. All Rights Reserved.
 */

package com.rottentomatoes.movieapi.domain.requests.ems;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpMethod;

import com.rottentomatoes.movieapi.domain.clients.ems.EmsClient;
import com.rottentomatoes.movieapi.domain.requests.AbstractJsonRequest;

/**
 * Request class for deprecated {@link EmsClient}. Made so {@code EmsClient} can use {@link Client}
 * to have a central location that makes HTTP requests.
 * 
 * @author harry
 */
@Deprecated
public class EmsClientRequest extends AbstractJsonRequest {
    public static final String API_STATE_NAME = "EMS_CLIENT_REQUEST";

    public EmsClientRequest(final String absoluteUrl, final Map<String, Object> selectParams, final String authHeader) {
        super(HttpMethod.GET, absoluteUrl, prepareQueryParams(selectParams), prepareAuthenticationHttpHeader(authHeader),
                API_STATE_NAME);
    }

    private static Map<String, String> prepareQueryParams(Map<String, Object> selectParams) {
        Map<String, String> queryParams = new HashMap<>();
        for (String key : selectParams.keySet()) {
            Object value = selectParams.get(key);
            if (value != null) {
                queryParams.put(key, value.toString());
            }
        }
        return queryParams;
    }
}
