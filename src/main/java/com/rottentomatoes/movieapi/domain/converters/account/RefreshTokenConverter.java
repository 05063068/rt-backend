package com.rottentomatoes.movieapi.domain.converters.account;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.rottentomatoes.movieapi.domain.converters.AbstractConverter;
import com.rottentomatoes.movieapi.domain.model.account.RefreshToken;

public class RefreshTokenConverter implements AbstractConverter<RefreshToken> {

    private String response;

    public RefreshTokenConverter(String response) {
        this.response = response;
    }

    public RefreshToken convert() {
        RefreshToken refreshToken = new RefreshToken();
        if (response != null) {
            refreshToken.setToken(response);
            try {
                JWT jwt = JWT.decode(response);
                if (jwt != null) {
                    for (String name : jwt.getClaims().keySet()) {
                        switch (name) {
                            case "issuer":
                                refreshToken.setIssuer(jwt.getClaim(name).asString());
                                break;
                            case "tokenType":
                                refreshToken.setTokenType(jwt.getClaim(name).asString());
                                break;
                            case "appId":
                                refreshToken.setAppId(jwt.getClaim(name).asString());
                                break;
                            case "vipId":
                                refreshToken.setVipId(jwt.getClaim(name).asString());
                                break;
                            case "accountId":
                                refreshToken.setAccountId(jwt.getClaim(name).asString());
                                break;
                            case "exp":
                                refreshToken.setExpiresAt(jwt.getExpiresAt());
                                break;
                        }
                    }
                }
            } catch (JWTVerificationException e) {
            }
        }
        return refreshToken;
    }
}
