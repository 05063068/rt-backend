
package com.rottentomatoes.movieapi.domain.model;

import io.katharsis.resource.annotations.JsonApiResource;
import lombok.Getter;
import lombok.Setter;

@JsonApiResource(type = "tvEpisode")
@Getter
@Setter
public class TvEpisode extends AbstractModel {
    private String title;
}
