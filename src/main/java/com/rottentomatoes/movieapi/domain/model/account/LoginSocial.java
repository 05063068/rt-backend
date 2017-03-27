package com.rottentomatoes.movieapi.domain.model.account;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.katharsis.resource.annotations.JsonApiResource;
import lombok.Getter;
import lombok.Setter;

@JsonApiResource(type = "login-social")
@Getter
@Setter
@JsonIgnoreProperties({"id","identityToken","session","user"})
public class LoginSocial extends Login {

    protected String accessToken;
}