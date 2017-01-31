package com.rottentomatoes.movieapi.domain.model.ems;

import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.rottentomatoes.movieapi.domain.model.AbstractModel;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmsMovieRtInfoModel extends AbstractModel {
    protected String vanityUrl;
    protected String movieId;
    protected String title;
    protected Integer releaseYear;
    protected String status;
}
