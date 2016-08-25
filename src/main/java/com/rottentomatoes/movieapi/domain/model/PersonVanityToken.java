package com.rottentomatoes.movieapi.domain.model;

import io.katharsis.resource.annotations.JsonApiResource;
import lombok.Getter;
import lombok.Setter;

@JsonApiResource(type = "person-vanity-token")
@Getter
@Setter
public class PersonVanityToken extends AbstractModel {
    protected String personId;
}
