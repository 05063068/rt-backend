package com.rottentomatoes.movieapi.domain.model.account;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.katharsis.resource.annotations.JsonApiResource;
import lombok.Getter;
import lombok.Setter;

@JsonApiResource(type = "signup-email")
@Getter
@Setter
@JsonIgnoreProperties({"id","identityToken","session","user","accessToken"})
public class SignupEmail extends Signup {

    protected String email;
    protected String password;
    protected String firstName;
    protected String lastName;
}