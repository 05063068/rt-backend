package com.rottentomatoes.movieapi.domain.model;

import io.katharsis.resource.annotations.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.Map;

@JsonApiResource(type = "quote")
@Getter
@Setter
public class Quote extends AbstractModel {

    protected Date creationDate;
    protected List<Map<String,String>> lines;
}