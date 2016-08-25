package com.rottentomatoes.movieapi.domain.model;

import io.katharsis.resource.annotations.JsonApiResource;

import lombok.Getter;
import lombok.Setter;

@JsonApiResource(type = "vanity-token")
@Getter
@Setter
public class VanityToken extends AbstractModel {
    protected String movieId;
}
