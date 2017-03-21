package com.rottentomatoes.movieapi.domain.apicalldelegators.account;

import com.fasterxml.jackson.core.type.TypeReference;
import com.rottentomatoes.movieapi.domain.apicalldelegators.AbstractApiCall;
import com.rottentomatoes.movieapi.domain.clients.Client;
import com.rottentomatoes.movieapi.domain.converters.account.SessionConverter;
import com.rottentomatoes.movieapi.domain.converters.account.UserConverter;
import com.rottentomatoes.movieapi.domain.model.account.User;
import com.rottentomatoes.movieapi.domain.requests.commonidentity.AbstractCommonIdentityRequest;
import com.rottentomatoes.movieapi.domain.requests.commonidentity.UserProfileFromAccessTokenRequest;
import com.rottentomatoes.movieapi.domain.responses.commonidentity.UserProfileResponse;
import com.rottentomatoes.movieapi.utils.JsonUtilities;
import io.katharsis.queryParams.RequestParams;
import org.springframework.core.env.Environment;

public class UserApiCall extends AbstractApiCall {

    public UserApiCall(final Environment environment, final String function, final RequestParams requestParams) {
        super(environment, function, requestParams);
    }

    @SuppressWarnings("unchecked")
    @Override
    public User process() {
        User user = null;
        AbstractCommonIdentityRequest request = null;
        switch (fieldName) {
            // fetch user for given identity token
            case "token":
                String token = null;
                if (requestParams != null) {
                    if (requestParams.getFilters()!= null && requestParams.getFilters().containsKey("accessToken")) {
                        token = (String) requestParams.getFilters().get("accessToken");
                    }
                }
                if (token == null) {
                    // TODO throw invalid request error
                }
                request = new UserProfileFromAccessTokenRequest(environment, token);
                break;
            default:
                // TODO add request for fetching user for an ID
                return user;
        }
        UserProfileResponse userProfileResponse = JsonUtilities.deserialize(Client.makeApiCall(request),
                new TypeReference<UserProfileResponse>() {});
        user = new UserConverter(userProfileResponse).convert();

        return user;
    }
}
