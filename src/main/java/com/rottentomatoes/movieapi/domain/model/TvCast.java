package com.rottentomatoes.movieapi.domain.model;

import io.katharsis.resource.annotations.JsonApiResource;
import io.katharsis.resource.annotations.JsonApiToOne;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@JsonApiResource(type = "tvCast")
@Getter
@Setter
public class TvCast extends AbstractModel {

    protected String role;

    protected List<String> characters;

    @JsonApiToOne
    private Person person;

}
