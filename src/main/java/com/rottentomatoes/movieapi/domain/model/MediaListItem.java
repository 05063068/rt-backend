package com.rottentomatoes.movieapi.domain.model;


import io.katharsis.resource.annotations.JsonApiResource;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonApiResource(type = "mediaListItem")
@Getter
@Setter
public class MediaListItem extends AbstractModel {
    private String mediaListId;
    private String mediaId;
    private String mediaType;
    private Integer sortkey;
    @JsonDeserialize(using = DeSerializeZonedDateTime.class)
    private ZonedDateTime creationDate;
}
