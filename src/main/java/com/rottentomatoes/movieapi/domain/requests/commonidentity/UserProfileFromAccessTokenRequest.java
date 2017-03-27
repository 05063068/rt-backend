/**
 * Flixster Inc. Copyright (c) 2017. All Rights Reserved.
 */

package com.rottentomatoes.movieapi.domain.requests.commonidentity;

import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;

import java.util.HashMap;
import java.util.Map;

public class UserProfileFromAccessTokenRequest extends AbstractCommonIdentityRequest {
    public static final String API_STAT_NAME = "COMMON_IDENTITY_USER_PROFILE";

    public UserProfileFromAccessTokenRequest(final Environment environment, String accessToken) {
        super(environment, HttpMethod.GET, "commonaccount", "account/profile", null, prepareHttpHeaders(accessToken), null, API_STAT_NAME);
    }

    private static Map<String, String> prepareHttpHeaders(String accessToken) {
        if (accessToken != null) {
            Map<String, String> httpHeaders = new HashMap<>();
            httpHeaders.put("Access-Token", accessToken);
            return httpHeaders;
        }
        return null;
    }
}
