package com.rottentomatoes.movieapi.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.katharsis.resource.annotations.JsonApiResource;
import io.katharsis.resource.annotations.JsonApiToOne;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@JsonApiResource(type = "tvCast")
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class TvCast extends AbstractModel {

    protected String role;

    protected List<String> characterName;

    @JsonApiToOne
    private Person person;

}
