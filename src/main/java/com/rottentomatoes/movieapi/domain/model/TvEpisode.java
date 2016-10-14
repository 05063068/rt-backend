
package com.rottentomatoes.movieapi.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.katharsis.resource.annotations.JsonApiResource;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@JsonApiResource(type = "episode")
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class TvEpisode extends AbstractModel {
    protected String title;
    protected String vanityUrl;

//    protected String trailerUrl;
//    protected String posterUrl;

    protected int episodeNumber;
    protected String tvSeasonId;
    protected String network;
    protected String genre;
    protected String synopsis;
    protected String airDate;
    protected int runningTime;

    // complex (nested) attributes
    protected Map<String, Object> tomatometer;

}
