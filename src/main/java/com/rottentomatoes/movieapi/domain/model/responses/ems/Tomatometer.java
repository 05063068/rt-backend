/**
 * Flixster Inc. Copyright (c) 2017. All Rights Reserved.
 */

package com.rottentomatoes.movieapi.domain.model.responses.ems;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

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
public class Tomatometer {

    @JsonProperty("certifiedFresh")
    public Boolean certifiedFresh;
    
    @JsonProperty("numReviews")
    public Integer numReviews;
    
    @JsonProperty("tomatometer")
    public Integer tomatometer;

}
