package com.rottentomatoes.movieapi.domain.model.account;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.katharsis.resource.annotations.JsonApiResource;
import lombok.Getter;
import lombok.Setter;

@JsonApiResource(type = "signup")
@Getter
@Setter
@JsonIgnoreProperties({"id","identityToken","session","user","accessToken"})
public class Signup extends Login {

    // extra fields sent as payload during signup
    protected String lastName;
    protected String firstName;

}