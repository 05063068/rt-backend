
package com.rottentomatoes.movieapi.domain.model;

import io.katharsis.resource.annotations.JsonApiResource;
import io.katharsis.resource.annotations.JsonApiToOne;
import lombok.Getter;
import lombok.Setter;

@JsonApiResource(type = "movieFilmographyItem")
@Getter
@Setter
public class MovieFilmographyItem extends AbstractModel {

    protected Iterable<String> roles;

    protected Iterable<String> characters;

    @JsonApiToOne
    protected Movie movie;

}
