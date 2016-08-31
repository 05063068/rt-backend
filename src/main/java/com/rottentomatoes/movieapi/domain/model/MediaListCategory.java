package com.rottentomatoes.movieapi.domain.model;

import io.katharsis.resource.annotations.*;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@JsonApiResource(type = "mediaListCategory")
@Getter
@Setter
public class MediaListCategory extends AbstractModel {
    private String name;
    private ZonedDateTime creationDate;
    private String status;

    @JsonApiToOne
    @JsonApiLazy
    @JsonApiLookupIncludeAutomatically
    protected MediaList mediaList;
}