package com.rottentomatoes.movieapi.domain.model;

import io.katharsis.resource.annotations.JsonApiResource;
import lombok.Getter;
import lombok.Setter;

@JsonApiResource(type = "criticSupplementaryInfo")
@Getter
@Setter
public class CriticSupplementaryInfo extends AbstractModel {

    protected String location;
    protected String officialUrl;
    protected String bio;
    protected String favorites;
    protected String quotes;
}