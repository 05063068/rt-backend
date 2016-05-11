package com.rottentomatoes.movieapi.domain.model;

import io.katharsis.resource.annotations.JsonApiResource;
import lombok.Getter;
import lombok.Setter;

@JsonApiResource(type = "videoClip")
@Getter
@Setter
public class VideoClip extends AbstractModel {
    
    protected String title;
    protected String sourceId;
    protected String thumbUrl;
    protected Integer duration;
    protected String clipType;
    protected String source;
}