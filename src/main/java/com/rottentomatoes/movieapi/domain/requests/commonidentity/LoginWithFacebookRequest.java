/**
 * Flixster Inc. Copyright (c) 2017. All Rights Reserved.
 */

package com.rottentomatoes.movieapi.domain.requests.commonidentity;

import com.rottentomatoes.movieapi.domain.model.account.LoginSocial;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;

public class LoginWithFacebookRequest extends AbstractCommonIdentityRequest {
    public static final String API_STAT_NAME = "COMMON_IDENTITY_FACEBOOK_LOGIN";

    public LoginWithFacebookRequest(final Environment environment, LoginSocial loginObject) {
        super(environment, HttpMethod.POST, "commonaccount", "facebook/login", null, null, loginObject, API_STAT_NAME);
    }

}
