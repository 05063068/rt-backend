package com.rottentomatoes.movieapi.domain.model;

import io.katharsis.resource.annotations.JsonApiId;
import io.katharsis.resource.annotations.JsonApiResource;
import lombok.Getter;
import lombok.Setter;

@JsonApiResource(type = "publication")
@Getter 
@Setter
public class Publication {

    @JsonApiId
    protected Long id;

    protected String name;

}
