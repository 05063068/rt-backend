/**
 * Flixster Inc. Copyright (c) 2017. All Rights Reserved.
 */

package com.rottentomatoes.movieapi.domain.requests.commonidentity;

import com.rottentomatoes.movieapi.domain.model.account.PasswordReset;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;

public class PasswordResetRequest extends AbstractCommonIdentityRequest {
    public static final String API_STAT_NAME = "COMMON_IDENTITY_PASSWORD_RESET";

    public PasswordResetRequest(final Environment environment, PasswordReset passwordResetObject) {
        super(environment, HttpMethod.PUT, "commonaccount", "user/sendemailtoresetpassword", null, null, passwordResetObject, API_STAT_NAME);
    }

}
