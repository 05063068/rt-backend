package com.rottentomatoes.movieapi.domain.apicalldelegators.account;

import com.fasterxml.jackson.core.type.TypeReference;
import com.rottentomatoes.movieapi.domain.apicalldelegators.AbstractApiCall;
import com.rottentomatoes.movieapi.domain.clients.Client;
import com.rottentomatoes.movieapi.domain.converters.account.IdentityTokenConverter;
import com.rottentomatoes.movieapi.domain.converters.account.SessionConverter;
import com.rottentomatoes.movieapi.domain.converters.account.UserConverter;
import com.rottentomatoes.movieapi.domain.model.account.LoginEmail;
import com.rottentomatoes.movieapi.domain.model.account.Login;
import com.rottentomatoes.movieapi.domain.model.account.LoginSocial;
import com.rottentomatoes.movieapi.domain.requests.commonidentity.AbstractCommonIdentityRequest;
import com.rottentomatoes.movieapi.domain.requests.commonidentity.LoginWithEmailRequest;
import com.rottentomatoes.movieapi.domain.requests.commonidentity.LoginWithFacebookRequest;
import com.rottentomatoes.movieapi.domain.requests.commonidentity.SessionFromIdentityTokenRequest;
import com.rottentomatoes.movieapi.domain.requests.commonidentity.UserProfileFromAccessTokenRequest;
import com.rottentomatoes.movieapi.domain.responses.commonidentity.IdentityTokenResponse;
import com.rottentomatoes.movieapi.domain.responses.commonidentity.SessionResponse;
import com.rottentomatoes.movieapi.domain.responses.commonidentity.UserProfileResponse;
import com.rottentomatoes.movieapi.utils.JsonUtilities;
import org.springframework.core.env.Environment;

public class LoginApiCall extends AbstractApiCall {

    private Login loginObject;

    public LoginApiCall(final Environment environment, final Login loginObject) {
        super(environment, null, null);
        this.loginObject = loginObject;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Login process() {
        Login response = new Login();
        if (hasLoginFieldsPopulated()) {
            AbstractCommonIdentityRequest request = null;

            // fetch identity token
            if (hasEmailFieldsPopulated()) {
                request = new LoginWithEmailRequest(environment, (LoginEmail) loginObject);
            } else if (hasSocialFieldsPopulated()) {
                request = new LoginWithFacebookRequest(environment, (LoginSocial) loginObject);
            } else {
                // TODO throw invalid request error
                return null;
            }

            IdentityTokenResponse idTokenResponse = JsonUtilities.deserialize(Client.makeApiCall(request),
                        new TypeReference<IdentityTokenResponse>() {});
            response.setIdentityToken(new IdentityTokenConverter(idTokenResponse).convert());

            // fetch session given identityToken
            if (idTokenResponse != null) {
                request = new SessionFromIdentityTokenRequest(environment, idTokenResponse.getIdentityToken());
                SessionResponse sessionResponse = JsonUtilities.deserialize(Client.makeApiCall(request),
                        new TypeReference<SessionResponse>() {
                        });
                response.setSession(new SessionConverter(sessionResponse).convert());

                // fetch user profile given accessToken
                if (sessionResponse != null) {
                    request = new UserProfileFromAccessTokenRequest(environment, sessionResponse.getAccessToken());
                    UserProfileResponse userProfileResponse = JsonUtilities.deserialize(Client.makeApiCall(request),
                            new TypeReference<UserProfileResponse>() {
                            });
                    response.setUser(new UserConverter(userProfileResponse).convert());
                }
            }
        } else {
            // TODO throw invalid request error
        }

//        clearPassword();
        return response;
    }

    private boolean hasLoginFieldsPopulated() {
        return hasEmailFieldsPopulated() || hasSocialFieldsPopulated();
    }

    private boolean hasEmailFieldsPopulated() {
        return loginObject != null && loginObject instanceof LoginEmail && ((LoginEmail) loginObject).getEmail() != null && ((LoginEmail) loginObject).getPassword() != null;
    }

    private boolean hasSocialFieldsPopulated() {
        return loginObject != null && loginObject instanceof LoginSocial && ((LoginSocial) loginObject).getAccessToken() != null;
    }

//    private void clearPassword() {
//        loginObject.setPassword(null);
//    }

}
