package com.rottentomatoes.movieapi.domain.payloads;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.rottentomatoes.movieapi.domain.model.AbstractModel;
import com.rottentomatoes.movieapi.domain.model.account.Login;
import io.katharsis.resource.annotations.JsonApiResource;
import lombok.Getter;
import lombok.Setter;

@JsonApiResource(type = "login-email")
@Getter
@Setter
@JsonIgnoreProperties({"id","identityToken","session","user"})
public class LoginEmail extends Login {

    protected String email;
    protected String password;
}