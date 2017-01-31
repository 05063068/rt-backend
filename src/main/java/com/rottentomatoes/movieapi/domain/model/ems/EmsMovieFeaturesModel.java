package com.rottentomatoes.movieapi.domain.model.ems;

import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.rottentomatoes.movieapi.domain.model.AbstractModel;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmsMovieFeaturesModel extends AbstractModel {
    protected Integer rtId;
    protected EmsTomatometerModel tomatometer;
    protected EmsMovieRtInfoModel rtInfo;
}
