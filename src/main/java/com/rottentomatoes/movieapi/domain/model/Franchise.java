package com.rottentomatoes.movieapi.domain.model;

import io.katharsis.resource.annotations.JsonApiResource;
import lombok.Getter;
import lombok.Setter;

@JsonApiResource(type = "franchise")
@Getter
@Setter
public class Franchise extends AbstractModel {

    protected String name;
    protected Integer mainImageId;
    protected Integer bannerImageId;

}
