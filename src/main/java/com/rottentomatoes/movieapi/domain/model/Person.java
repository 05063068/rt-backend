package com.rottentomatoes.movieapi.domain.model;

import io.katharsis.resource.annotations.JsonApiId;
import io.katharsis.resource.annotations.JsonApiResource;
import lombok.Getter;
import lombok.Setter;

@JsonApiResource(type = "person")
@Getter 
@Setter
public class Person {

    @JsonApiId
    protected Long id;

    protected String name;

}
