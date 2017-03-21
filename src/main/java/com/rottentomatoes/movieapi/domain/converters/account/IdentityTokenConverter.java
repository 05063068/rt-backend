package com.rottentomatoes.movieapi.domain.converters.account;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.rottentomatoes.movieapi.domain.converters.AbstractConverter;
import com.rottentomatoes.movieapi.domain.model.account.IdentityToken;
import com.rottentomatoes.movieapi.domain.responses.commonidentity.IdentityTokenResponse;

public class IdentityTokenConverter implements AbstractConverter<IdentityToken> {

    private String response;

    public IdentityTokenConverter(String response) {
        this.response = response;
    }

    public IdentityTokenConverter(IdentityTokenResponse response) {
        if (response != null) {
            this.response = response.getIdentityToken();
        }
    }

    public IdentityToken convert() {
        IdentityToken identityToken = new IdentityToken();
        if (response != null) {
            identityToken.setToken(response);
            try {
                JWT jwt = JWT.decode(response);
                if (jwt != null) {
                    for (String name : jwt.getClaims().keySet()) {
                        switch (name) {
                            case "issuer":
                                identityToken.setIssuer(jwt.getClaim(name).asString());
                                break;
                            case "tokenType":
                                identityToken.setTokenType(jwt.getClaim(name).asString());
                                break;
                            case "appId":
                                identityToken.setAppId(jwt.getClaim(name).asString());
                                break;
                            case "userKey":
                                identityToken.setUserKey(jwt.getClaim(name).asString());
                                break;
                            case "exp":
                                identityToken.setExpiresAt(jwt.getExpiresAt());
                                break;
                        }
                    }
                }
            } catch (JWTVerificationException e) {
            }
        }
        return identityToken;
    }
}
