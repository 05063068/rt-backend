/**
 * Flixster Inc. Copyright (c) 2017. All Rights Reserved.
 */

package com.rottentomatoes.movieapi.domain.requests.commonidentity;

import com.rottentomatoes.movieapi.domain.model.account.Signup;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;

public class SignupWithEmailRequest extends AbstractCommonIdentityRequest {
    public static final String API_STAT_NAME = "COMMON_IDENTITY_SIGNUP";

    public SignupWithEmailRequest(final Environment environment, Signup signupObject) {
        super(environment, HttpMethod.POST, "commonaccount", "signup", null, null, signupObject, API_STAT_NAME);
    }

}
