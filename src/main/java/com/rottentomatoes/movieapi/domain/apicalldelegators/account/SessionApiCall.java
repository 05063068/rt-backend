package com.rottentomatoes.movieapi.domain.apicalldelegators.account;

import com.fasterxml.jackson.core.type.TypeReference;
import com.rottentomatoes.movieapi.domain.apicalldelegators.AbstractApiCall;
import com.rottentomatoes.movieapi.domain.clients.Client;
import com.rottentomatoes.movieapi.domain.converters.account.SessionConverter;
import com.rottentomatoes.movieapi.domain.model.account.Session;
import com.rottentomatoes.movieapi.domain.requests.commonidentity.AbstractCommonIdentityRequest;
import com.rottentomatoes.movieapi.domain.requests.commonidentity.SessionFromIdentityTokenRequest;
import com.rottentomatoes.movieapi.domain.requests.commonidentity.SessionFromRefreshTokenRequest;
import com.rottentomatoes.movieapi.domain.responses.commonidentity.SessionResponse;
import com.rottentomatoes.movieapi.utils.JsonUtilities;
import io.katharsis.queryParams.RequestParams;
import org.springframework.core.env.Environment;

public class SessionApiCall extends AbstractApiCall {

    public SessionApiCall(final Environment environment, final String function, final RequestParams requestParams) {
        super(environment, function, requestParams);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Session process() {
        String token = null;
        Session session = null;
        if (requestParams != null) {
            if (requestParams.getFilters()!= null && requestParams.getFilters().containsKey("token")) {
                token = (String) requestParams.getFilters().get("token");
            }
        }
        if (token != null) {
            AbstractCommonIdentityRequest request = null;
            switch (fieldName) {
                case "fetch":
                    request = new SessionFromIdentityTokenRequest(environment, token);
                    break;
                case "refresh":
                    request = new SessionFromRefreshTokenRequest(environment, token);
                    break;
                default:
                    return session;
            }
            SessionResponse sessionResponse = JsonUtilities.deserialize(Client.makeApiCall(request),
                    new TypeReference<SessionResponse>() {});
            session = new SessionConverter(sessionResponse).convert();

        } else {
            // TODO throw invalid request error
        }

        return session;
    }
}
