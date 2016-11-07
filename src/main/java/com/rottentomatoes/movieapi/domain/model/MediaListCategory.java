package com.rottentomatoes.movieapi.domain.model;

import io.katharsis.resource.annotations.*;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonApiResource(type = "mediaListCategory")
@Getter
@Setter
public class MediaListCategory extends AbstractModel {
    private String name;
    @JsonDeserialize(using = DeSerializeZonedDateTime.class)
    private ZonedDateTime creationDate;
    private String status;

    @JsonApiToOne
    @JsonApiLazy
    @JsonApiLookupIncludeAutomatically
    protected MediaList mediaList;
}