package com.rottentomatoes.movieapi.domain.model;


import io.katharsis.resource.annotations.JsonApiResource;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@JsonApiResource(type = "mediaListItem")
@Getter
@Setter
public class MediaListItem extends AbstractModel {
    private String mediaListId;
    private String mediaId;
    private String mediaType;
    private Integer sortkey;
    private ZonedDateTime creationDate;
}
