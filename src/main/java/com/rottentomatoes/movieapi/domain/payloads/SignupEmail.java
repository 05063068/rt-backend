package com.rottentomatoes.movieapi.domain.payloads;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.rottentomatoes.movieapi.domain.model.account.Signup;
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