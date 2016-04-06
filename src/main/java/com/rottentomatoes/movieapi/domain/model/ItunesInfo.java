package com.rottentomatoes.movieapi.domain.model;

import io.katharsis.resource.annotations.JsonApiResource;
import lombok.Getter;
import lombok.Setter;

@JsonApiResource(type = "itunesInfo")
@Getter
@Setter
public class ItunesInfo extends AbstractModel {
    protected String mediaProfile;
    protected String type;
    protected String currency;
    protected Double price;
    protected String country;
}
