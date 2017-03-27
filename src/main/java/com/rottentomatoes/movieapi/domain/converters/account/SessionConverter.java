package com.rottentomatoes.movieapi.domain.converters.account;

import com.rottentomatoes.movieapi.domain.converters.AbstractConverter;
import com.rottentomatoes.movieapi.domain.model.account.Session;
import com.rottentomatoes.movieapi.domain.responses.commonidentity.SessionResponse;

public class SessionConverter implements AbstractConverter<Session> {

    private SessionResponse response;

    public SessionConverter(SessionResponse response) {
        this.response = response;
    }

    public Session convert() {
        Session session = new Session();
        if (response != null) {
            session.setAccessToken(new AccessTokenConverter(response.getAccessToken()).convert());
            session.setRefreshToken(new RefreshTokenConverter(response.getRefreshToken()).convert());
            session.setExpiresIn(response.getExpiresIn());
        }
        return session;
    }
}
