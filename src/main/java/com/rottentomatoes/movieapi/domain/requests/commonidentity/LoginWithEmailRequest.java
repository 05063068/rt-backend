/**
 * Flixster Inc. Copyright (c) 2017. All Rights Reserved.
 */

package com.rottentomatoes.movieapi.domain.requests.commonidentity;

import com.rottentomatoes.movieapi.domain.payloads.LoginEmail;
import lombok.Getter;
import lombok.Setter;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;

@Getter
@Setter
public class LoginWithEmailRequest extends AbstractCommonIdentityRequest {
    public static final String API_STAT_NAME = "COMMON_IDENTITY_EMAIL_LOGIN";

    // payload fields
    protected String email;
    protected String password;

    public LoginWithEmailRequest(final Environment environment, LoginEmail loginObject) {
        super(environment, HttpMethod.POST, "commonaccount", "login", null, null, API_STAT_NAME);
        preparePayload(loginObject);
    }

    private void preparePayload(LoginEmail loginObject) {
        this.setEmail(loginObject.getEmail());
        this.setPassword(loginObject.getPassword());
    }
}
