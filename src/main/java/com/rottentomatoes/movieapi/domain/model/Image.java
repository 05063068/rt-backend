package com.rottentomatoes.movieapi.domain.model;


import io.katharsis.resource.annotations.JsonApiResource;
import lombok.Getter;
import lombok.Setter;

@JsonApiResource(type = "image")
@Getter
@Setter
public class Image extends AbstractModel {
    protected Integer originalHeight;
    protected Integer originalWidth;
    protected String thumborId;
    protected String format;
    protected String mediaType;
    protected String aspectRatio;
}
