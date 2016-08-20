
package com.rottentomatoes.movieapi.domain.model;

import io.katharsis.resource.annotations.JsonApiResource;
import io.katharsis.resource.annotations.JsonApiToOne;
import lombok.Getter;
import lombok.Setter;

@JsonApiResource(type = "filmographyItem")
@Getter
@Setter
public class TvFilmographyItem extends AbstractModel {

    @JsonApiToOne
    protected TvCast tvCast;

    @JsonApiToOne
    protected TvSeries tvSeries;
}
