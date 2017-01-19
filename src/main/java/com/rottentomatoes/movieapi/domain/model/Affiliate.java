package com.rottentomatoes.movieapi.domain.model;

import io.katharsis.resource.annotations.JsonApiResource;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonApiResource(type = "affiliate")
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Affiliate extends AbstractModel {

    protected String url;
    protected String affiliateName;
    protected String specialStatus;
}
