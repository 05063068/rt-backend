package com.rottentomatoes.movieapi.domain.model;

import io.katharsis.resource.annotations.JsonApiResource;
import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

// There is no repository for this because it will be optionally filled in when the movie info gets created
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class MovieBoxOfficeInfo extends AbstractModel {
    protected Integer rank;
    protected Integer lastRank;
    protected Integer theaterAverage;
    protected Integer totalGross;
    protected String title;
    protected Integer weekendGross;
    protected Integer screens;
    protected Integer weekNumber;
    protected Integer openingDays;

}
