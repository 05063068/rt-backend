/**
 * Flixster Inc. Copyright (c) 2017. All Rights Reserved.
 */

package com.rottentomatoes.movieapi.domain.requests.commonidentity;

import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;

import java.util.HashMap;
import java.util.Map;

public class SessionFromRefreshTokenRequest extends AbstractCommonIdentityRequest {
    public static final String API_STAT_NAME = "COMMON_IDENTITY_SESSION_REFRESH";

    public SessionFromRefreshTokenRequest(final Environment environment, String refreshToken) {
        super(environment, HttpMethod.GET, "commonauth", "refreshAccessToken", null, prepareHttpHeaders(refreshToken), null, API_STAT_NAME);
    }

    private static Map<String, String> prepareHttpHeaders(String refreshToken) {
        if (refreshToken != null) {
            Map<String, String> httpHeaders = new HashMap<>();
            httpHeaders.put("Refresh-Token", refreshToken);
            return httpHeaders;
        }
        return null;
    }
}
