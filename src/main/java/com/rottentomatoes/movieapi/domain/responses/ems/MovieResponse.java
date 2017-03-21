/**
 * Flixster Inc. Copyright (c) 2017. All Rights Reserved.
 */

package com.rottentomatoes.movieapi.domain.responses.ems;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.rottentomatoes.movieapi.domain.converters.AbstractConverter;
import lombok.Getter;
import lombok.Setter;

/**
 * Class auto-generated via jsonschema2pojo
 * 
 * @author harry
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class MovieResponse {

    @JsonProperty("totalGross")
    private Integer totalGross;

    @JsonProperty("title")
    private String title;

    @JsonProperty("weekNumber")
    private Integer weekNumber;

    @JsonProperty("openingDays")
    private Integer openingDays;

    @JsonProperty("weekendGross")
    private Integer weekendGross;

    @JsonProperty("screens")
    private Integer screens;

    @JsonProperty("rank")
    private Integer rank;

    @JsonProperty("rt_id")
    private Integer rtId;

    @JsonProperty("rt_info")
    private RtInfoResponse rtInfo;

    @JsonProperty("tomatometer")
    private TomatometerResponse tomatometer;

    @JsonProperty("mpaaRating")
    private MpaaRatingResponse mpaaRating;

    @JsonProperty("lastRank")
    private Integer lastRank;

}
