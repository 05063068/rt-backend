package com.rottentomatoes.movieapi.domain.apicalldelegators.account;

import com.fasterxml.jackson.core.type.TypeReference;
import com.rottentomatoes.movieapi.domain.apicalldelegators.AbstractApiCall;
import com.rottentomatoes.movieapi.domain.clients.Client;
import com.rottentomatoes.movieapi.domain.converters.account.IdentityTokenConverter;
import com.rottentomatoes.movieapi.domain.converters.account.SessionConverter;
import com.rottentomatoes.movieapi.domain.converters.account.UserConverter;
import com.rottentomatoes.movieapi.domain.model.account.Signup;
import com.rottentomatoes.movieapi.domain.requests.commonidentity.AbstractCommonIdentityRequest;
import com.rottentomatoes.movieapi.domain.requests.commonidentity.SignupWithEmailRequest;
import com.rottentomatoes.movieapi.domain.requests.commonidentity.SessionFromIdentityTokenRequest;
import com.rottentomatoes.movieapi.domain.requests.commonidentity.UserProfileFromAccessTokenRequest;
import com.rottentomatoes.movieapi.domain.responses.commonidentity.IdentityTokenResponse;
import com.rottentomatoes.movieapi.domain.responses.commonidentity.SessionResponse;
import com.rottentomatoes.movieapi.domain.responses.commonidentity.UserProfileResponse;
import com.rottentomatoes.movieapi.utils.JsonUtilities;
import org.springframework.core.env.Environment;

public class SignupApiCall extends AbstractApiCall {

    private Signup signupObject;

    public SignupApiCall(final Environment environment, final Signup signupObject) {
        super(environment, null, null);
        this.signupObject = signupObject;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Signup process() {
        if (hasSignupFieldsPopulated()) {
            // fetch identity token
            AbstractCommonIdentityRequest request = new SignupWithEmailRequest(environment, signupObject);
            IdentityTokenResponse idTokenResponse = JsonUtilities.deserialize(Client.makeApiCall(request),
                        new TypeReference<IdentityTokenResponse>() {});
            signupObject.setIdentityToken(new IdentityTokenConverter(idTokenResponse).convert());

            // fetch session given identityToken
            if (idTokenResponse != null) {
                request = new SessionFromIdentityTokenRequest(environment, idTokenResponse.getIdentityToken());
                SessionResponse sessionResponse = JsonUtilities.deserialize(Client.makeApiCall(request),
                        new TypeReference<SessionResponse>() {});
                signupObject.setSession(new SessionConverter(sessionResponse).convert());

                // fetch user profile given accessToken
                if (sessionResponse != null) {
                    request = new UserProfileFromAccessTokenRequest(environment, sessionResponse.getAccessToken());
                    UserProfileResponse userProfileResponse = JsonUtilities.deserialize(Client.makeApiCall(request),
                            new TypeReference<UserProfileResponse>() {});
                    signupObject.setUser(new UserConverter(userProfileResponse).convert());
                }
            }
        } else {
            // TODO throw invalid request error
        }

        clearPassword();
        return signupObject;
    }

    private boolean hasSignupFieldsPopulated() {
        return signupObject != null &&
                signupObject.getEmail() != null &&
                signupObject.getPassword() != null &&
                signupObject.getFirstName() != null &&
                signupObject.getLastName() != null;
    }

    private void clearPassword() {
        signupObject.setPassword(null);
    }

}
