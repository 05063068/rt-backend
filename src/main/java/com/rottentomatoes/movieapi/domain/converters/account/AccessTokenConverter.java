package com.rottentomatoes.movieapi.domain.converters.account;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.rottentomatoes.movieapi.domain.converters.AbstractConverter;
import com.rottentomatoes.movieapi.domain.model.account.AccessToken;

public class AccessTokenConverter implements AbstractConverter<AccessToken> {

    private String response;

    public AccessTokenConverter(String response) {
        this.response = response;
    }

    public AccessToken convert() {
        AccessToken accessToken = new AccessToken();
        if (response != null) {
            accessToken.setToken(response);
            try {
                JWT jwt = JWT.decode(response);
                if (jwt != null) {
                    for (String name : jwt.getClaims().keySet()) {
                        switch (name) {
                            case "issuer":
                                accessToken.setIssuer(jwt.getClaim(name).asString());
                                break;
                            case "tokenType":
                                accessToken.setTokenType(jwt.getClaim(name).asString());
                                break;
                            case "appId":
                                accessToken.setAppId(jwt.getClaim(name).asString());
                                break;
                            case "vipId":
                                accessToken.setVipId(jwt.getClaim(name).asString());
                                break;
                            case "accountId":
                                accessToken.setAccountId(jwt.getClaim(name).asString());
                                break;
                            case "email":
                                accessToken.setEmail(jwt.getClaim(name).asString());
                                break;
                            case "userKey":
                                accessToken.setUserKey(jwt.getClaim(name).asString());
                                break;
                            case "exp":
                                accessToken.setExpiresAt(jwt.getExpiresAt());
                                break;
                        }
                    }
                }
            } catch (JWTVerificationException e) {
            }
        }
        return accessToken;
    }
}
