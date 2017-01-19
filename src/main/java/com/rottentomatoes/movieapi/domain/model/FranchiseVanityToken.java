package com.rottentomatoes.movieapi.domain.model;

import io.katharsis.resource.annotations.JsonApiResource;
import lombok.Getter;
import lombok.Setter;

@JsonApiResource(type = "franchise-vanity-token")
@Getter
@Setter
public class FranchiseVanityToken extends AbstractModel {
    protected String franchiseId;
}
