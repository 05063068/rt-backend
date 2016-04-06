package com.rottentomatoes.movieapi.domain.model;

import io.katharsis.resource.annotations.JsonApiResource;
import lombok.Getter;
import lombok.Setter;

@JsonApiResource(type = "videoClip")
@Getter
@Setter
public class VideoClip extends AbstractModel {
    protected Integer duration;
    protected String title;
    protected String thumbUrl;
    protected String format;
    protected String source;
    protected String url;
    protected String language;
    protected String status;
    protected Integer favoriteCount;
}
