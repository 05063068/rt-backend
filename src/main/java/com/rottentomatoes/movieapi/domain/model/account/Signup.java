package com.rottentomatoes.movieapi.domain.model.account;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.katharsis.resource.annotations.JsonApiResource;
import lombok.Getter;
import lombok.Setter;

@JsonApiResource(type = "signup")
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Signup extends Login {

}