package com.rottentomatoes.movieapi.domain.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.katharsis.resource.annotations.JsonApiResource;
import io.katharsis.resource.annotations.JsonApiToOne;
import lombok.Getter;
import lombok.Setter;

@JsonApiResource(type = "movieCast")
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class MovieCast extends AbstractModel {

    protected String role;

    protected List<String> characters;

    @JsonApiToOne
    private Person person;

}
