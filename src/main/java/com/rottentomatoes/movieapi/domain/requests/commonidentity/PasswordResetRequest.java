/**
 * Flixster Inc. Copyright (c) 2017. All Rights Reserved.
 */

package com.rottentomatoes.movieapi.domain.requests.commonidentity;

import com.rottentomatoes.movieapi.domain.model.account.PasswordReset;
import lombok.Getter;
import lombok.Setter;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;

@Getter
@Setter
public class PasswordResetRequest extends AbstractCommonIdentityRequest {
    public static final String API_STAT_NAME = "COMMON_IDENTITY_PASSWORD_RESET";

    protected String email;

    public PasswordResetRequest(final Environment environment, PasswordReset passwordResetObject) {
        super(environment, HttpMethod.PUT, "commonaccount", "user/sendemailtoresetpassword", null, null, API_STAT_NAME);
        preparePayload(passwordResetObject);
    }

    private void preparePayload(PasswordReset passwordResetObject) {
        this.setEmail(passwordResetObject.getEmail());
    }

}
