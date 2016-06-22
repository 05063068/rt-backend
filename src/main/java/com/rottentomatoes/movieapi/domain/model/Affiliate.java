package com.rottentomatoes.movieapi.domain.model;

import io.katharsis.resource.annotations.JsonApiResource;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@JsonApiResource(type = "affiliate")
@Getter
@Setter
public class Affiliate extends AbstractModel {

    protected String url;
    protected String affiliateName;
    protected String specialStatus;
}
