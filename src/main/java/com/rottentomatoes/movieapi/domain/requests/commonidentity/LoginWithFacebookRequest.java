/**
 * Flixster Inc. Copyright (c) 2017. All Rights Reserved.
 */

package com.rottentomatoes.movieapi.domain.requests.commonidentity;

import com.rottentomatoes.movieapi.domain.payloads.LoginSocial;
import lombok.Getter;
import lombok.Setter;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;

@Getter
@Setter
public class LoginWithFacebookRequest extends AbstractCommonIdentityRequest {
    public static final String API_STAT_NAME = "COMMON_IDENTITY_FACEBOOK_LOGIN";

    // payload fields
    protected String accessToken;

    public LoginWithFacebookRequest(final Environment environment, LoginSocial loginObject) {
        super(environment, HttpMethod.POST, "commonaccount", "facebook/login", null, null, API_STAT_NAME);
        preparePayload(loginObject);
    }

    private void preparePayload(LoginSocial loginObject) {
        this.setAccessToken(loginObject.getToken());
    }
}
