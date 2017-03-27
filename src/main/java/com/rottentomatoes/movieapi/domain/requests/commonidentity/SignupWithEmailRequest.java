/**
 * Flixster Inc. Copyright (c) 2017. All Rights Reserved.
 */

package com.rottentomatoes.movieapi.domain.requests.commonidentity;

import com.rottentomatoes.movieapi.domain.payloads.SignupEmail;
import lombok.Getter;
import lombok.Setter;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;

@Getter
@Setter
public class SignupWithEmailRequest extends AbstractCommonIdentityRequest {
    public static final String API_STAT_NAME = "COMMON_IDENTITY_SIGNUP";

    // payload fields
    protected String email;
    protected String password;
    protected String firstName;
    protected String lastName;

    public SignupWithEmailRequest(final Environment environment, SignupEmail signupObject) {
        super(environment, HttpMethod.POST, "commonaccount", "signup", null, null, API_STAT_NAME);
        preparePayload(signupObject);
    }

    private void preparePayload(SignupEmail signupObject) {
        this.setEmail(signupObject.getEmail());
        this.setPassword(signupObject.getPassword());
        this.setFirstName(signupObject.getFirstName());
        this.setLastName(signupObject.getLastName());
    }
}
