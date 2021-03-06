package com.rottentomatoes.movieapi.domain.model;

import io.katharsis.resource.annotations.*;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonApiResource(type = "mediaList")
@Getter
@Setter
public class MediaList extends AbstractModel {
    private String categoryId;
    @JsonDeserialize(using = DeSerializeZonedDateTime.class)
    private ZonedDateTime startDate;
    private String title;

    @JsonApiToMany
    @JsonApiLazy
    @JsonApiLookupIncludeAutomatically
    protected Iterable<MediaListItem> mediaListItems;
}
