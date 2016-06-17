package com.rottentomatoes.movieapi.domain.model;

import io.katharsis.resource.annotations.JsonApiResource;
import lombok.Getter;
import lombok.Setter;

@JsonApiResource(type = "tvSeries")
@Getter
@Setter
public class TvSeries extends AbstractModel {
    protected String title;
    protected String description;
}
