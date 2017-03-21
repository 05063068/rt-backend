/**
 * Flixster Inc. Copyright (c) 2017. All Rights Reserved.
 */

package com.rottentomatoes.movieapi.domain.responses.urating;

import java.time.ZonedDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.rottentomatoes.movieapi.domain.model.DeSerializeZonedDateTime;

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
public class UserRatingResponse {

    @JsonProperty("movie_id")
    public Long movieId;

    @JsonProperty("user_id")
    public Long userId;

    @JsonProperty("comment")
    public String comment;

    @JsonProperty("rating")
    public Double rating;

    @JsonProperty("is_super_reviewer")
    public Boolean isSuperReviewer;

    @JsonDeserialize(using = DeSerializeZonedDateTime.class)
    @JsonProperty("last_rating_date")
    public ZonedDateTime lastRatingDate;

}
