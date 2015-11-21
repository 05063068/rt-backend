package com.rottentomatoes.movieapi.domain.model;

import io.katharsis.resource.annotations.JsonApiResource;
import lombok.Getter;
import lombok.Setter;

@JsonApiResource(type = "critic")
@Getter 
@Setter
public class Critic extends AbstractModel {
    protected String name;

}
