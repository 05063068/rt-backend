package com.rottentomatoes.movieapi.domain.model;

import java.util.List;

import io.katharsis.resource.annotations.JsonApiResource;
import io.katharsis.resource.annotations.JsonApiToOne;
import com.rottentomatoes.movieapi.domain.model.Person;
import lombok.Getter;
import lombok.Setter;

@JsonApiResource(type = "movieCast")
@Getter
@Setter
public class MovieCast extends AbstractModel {

    protected String role;

    protected List<String> characters;

    @JsonApiToOne
    private Person person;

}
