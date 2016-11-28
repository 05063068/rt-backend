package com.rottentomatoes.movieapi.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.katharsis.resource.annotations.JsonApiResource;
import lombok.Getter;
import lombok.Setter;

@JsonApiResource(type = "topTomatometer")
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class TopTomatometer extends AbstractModel {
    protected String numReviews;
    protected String freshCount;
    protected String rottenCount;
    protected String tomatometer;
    protected String averageScore;
    protected String episodesWithScore;
    protected String episodeAvgScore;
    protected String consensus;
    protected String state;
    protected String certifiedFresh;
}
