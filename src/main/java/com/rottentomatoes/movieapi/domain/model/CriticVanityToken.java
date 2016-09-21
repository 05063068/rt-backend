package com.rottentomatoes.movieapi.domain.model;

import io.katharsis.resource.annotations.JsonApiResource;
import lombok.Getter;
import lombok.Setter;

@JsonApiResource(type = "critic-vanity-token")
@Getter
@Setter
public class CriticVanityToken extends AbstractModel {
    protected String criticId;
}
