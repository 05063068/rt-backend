/**
 * Flixster Inc. Copyright (c) 2017. All Rights Reserved.
 */

package com.rottentomatoes.movieapi.domain.requests.commonidentity;

import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;

import java.util.HashMap;
import java.util.Map;

public class SessionFromIdentityTokenRequest extends AbstractCommonIdentityRequest {
    public static final String API_STAT_NAME = "COMMON_IDENTITY_SESSION_FETCH";

    public SessionFromIdentityTokenRequest(final Environment environment, String identityToken) {
        super(environment, HttpMethod.GET, "commonauth", "session", null, prepareHttpHeaders(identityToken), API_STAT_NAME);
    }

    private static Map<String, String> prepareHttpHeaders(String identityToken) {
        if (identityToken != null) {
            Map<String, String> httpHeaders = new HashMap<>();
            httpHeaders.put("Identity-Token", identityToken);
            return httpHeaders;
        }
        return null;
    }
}
