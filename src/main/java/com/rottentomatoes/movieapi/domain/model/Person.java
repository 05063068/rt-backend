package com.rottentomatoes.movieapi.domain.model;

import io.katharsis.resource.annotations.JsonApiResource;
import io.katharsis.resource.annotations.JsonApiToOne;
import lombok.Getter;
import lombok.Setter;

@JsonApiResource(type = "person")
@Getter
@Setter
public class Person extends AbstractModel {
    protected String name;
    
    protected String vanity;

    @JsonApiToOne
    protected Image mainImage;
}
