package com.rottentomatoes.movieapi.domain.model;

import io.katharsis.resource.annotations.JsonApiResource;
import lombok.Getter;
import lombok.Setter;

@JsonApiResource(type = "videoClip")
@Getter 
@Setter
public class VideoClip extends AbstractModel{

    protected String url;

}
