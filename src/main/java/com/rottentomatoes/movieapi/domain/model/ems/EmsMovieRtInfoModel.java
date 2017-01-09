package com.rottentomatoes.movieapi.domain.model.ems;

import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.rottentomatoes.movieapi.domain.model.AbstractModel;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmsMovieRtInfoModel extends AbstractModel {
    protected String rtId;
    protected String consensus;
    protected Integer rottenCount;
    protected Integer movieReleaseYear;
    protected Integer tomatometer;
    protected Integer numReviews;
    protected Boolean pendingCertified;
    protected Boolean certifiedFresh;
    protected Integer freshCount;
}
