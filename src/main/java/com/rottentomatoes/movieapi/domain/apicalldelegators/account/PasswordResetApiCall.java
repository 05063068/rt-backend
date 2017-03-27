package com.rottentomatoes.movieapi.domain.apicalldelegators.account;

import com.fasterxml.jackson.core.type.TypeReference;
import com.rottentomatoes.movieapi.domain.apicalldelegators.AbstractApiCall;
import com.rottentomatoes.movieapi.domain.clients.Client;
import com.rottentomatoes.movieapi.domain.model.account.PasswordReset;
import com.rottentomatoes.movieapi.domain.requests.commonidentity.AbstractCommonIdentityRequest;
import com.rottentomatoes.movieapi.domain.requests.commonidentity.PasswordResetRequest;
import com.rottentomatoes.movieapi.utils.JsonUtilities;
import org.springframework.core.env.Environment;

public class PasswordResetApiCall extends AbstractApiCall {

    private PasswordReset passwordResetObject;

    public PasswordResetApiCall(final Environment environment, final PasswordReset passwordResetObject) {
        super(environment, null, null);
        this.passwordResetObject = passwordResetObject;
    }

    @SuppressWarnings("unchecked")
    @Override
    public PasswordReset process() {
        if (hasPasswordResetFieldsPopulated()) {
            AbstractCommonIdentityRequest request = new PasswordResetRequest(environment, passwordResetObject);
            PasswordReset response = JsonUtilities.deserialize(Client.makeApiCall(request), new TypeReference<PasswordReset>() {});
            if (response != null) {
                passwordResetObject = response;
            } else {
                // TODO throw error related to bad response
            }
        } else {
            // TODO throw invalid request error
        }
        return passwordResetObject;
    }

    private boolean hasPasswordResetFieldsPopulated() {
        return passwordResetObject != null && passwordResetObject.getEmail() != null;
    }

}
