package com.rottentomatoes.movieapi.domain.model;

import io.katharsis.resource.annotations.JsonApiResource;
import lombok.Getter;
import lombok.Setter;

@JsonApiResource(type = "person")
@Getter 
@Setter
public class Person extends AbstractModel{

    protected String name;

}
