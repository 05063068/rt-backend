/**
 * Flixster Inc. Copyright (c) 2017. All Rights Reserved.
 */

package com.rottentomatoes.movieapi.domain.requests.commonidentity;

import com.rottentomatoes.movieapi.domain.model.account.Login;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;

public class LoginWithEmailRequest extends AbstractCommonIdentityRequest {
    public static final String API_STAT_NAME = "COMMON_IDENTITY_EMAIL_LOGIN";

    public LoginWithEmailRequest(final Environment environment, Login loginObject) {
        super(environment, HttpMethod.POST, "commonaccount", "login", null, null, loginObject, API_STAT_NAME);
    }

}
