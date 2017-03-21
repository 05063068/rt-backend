package com.rottentomatoes.movieapi.domain.model.account;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.rottentomatoes.movieapi.domain.model.AbstractModel;
import io.katharsis.resource.annotations.JsonApiResource;
import lombok.Getter;
import lombok.Setter;

@JsonApiResource(type = "login")
@Getter
@Setter
@JsonIgnoreProperties({"id","identityToken","session","user"})
public class Login extends AbstractModel {

    // for normal email account
    protected String email;
    protected String password;

    // For social/facebook account
    protected String accessToken;

    // Retrieved Data
    protected IdentityToken identityToken;

    protected Session session;

    protected User user;

}