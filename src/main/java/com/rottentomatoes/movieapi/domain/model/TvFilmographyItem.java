
package com.rottentomatoes.movieapi.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.katharsis.resource.annotations.JsonApiResource;
import io.katharsis.resource.annotations.JsonApiToOne;
import lombok.Getter;
import lombok.Setter;

@JsonApiResource(type = "tvFilmographyItem")
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class TvFilmographyItem extends AbstractModel {

    protected Iterable<String> roles;

    protected Iterable<String> characters;

    @JsonApiToOne
    protected TvSeries tvSeries;
}
