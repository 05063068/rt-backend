package com.rottentomatoes.movieapi.domain.model;


import io.katharsis.resource.annotations.JsonApiResource;
import lombok.Getter;
import lombok.Setter;

@JsonApiResource(type = "image")
@Getter 
@Setter
public class Image extends AbstractModel {
    protected String url;
}
