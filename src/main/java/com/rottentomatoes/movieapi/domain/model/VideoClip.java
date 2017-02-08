package com.rottentomatoes.movieapi.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.katharsis.resource.annotations.JsonApiResource;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@JsonApiResource(type = "videoClip")
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class VideoClip extends AbstractModel {
    
    protected String title;
    protected String sourceId;
    protected String thumbUrl;
    protected Integer duration;
    protected String clipType;
    protected String source;
    @JsonDeserialize(using = DeSerializeZonedDateTime.class)
    protected ZonedDateTime creationDate;
}