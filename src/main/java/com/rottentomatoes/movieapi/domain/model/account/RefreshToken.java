package com.rottentomatoes.movieapi.domain.model.account;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.rottentomatoes.movieapi.domain.model.AbstractModel;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class RefreshToken extends AbstractModel {

    protected String token;

    // Decrypted from token
    protected String issuer;
    protected String tokenType;
    protected Date expiresAt;

    protected String appId;
    protected String vipId;
    protected String accountId;

}