package com.rottentomatoes.movieapi.domain.model;

import io.katharsis.resource.annotations.JsonApiResource;
import io.katharsis.resource.annotations.JsonApiToOne;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonApiResource(type = "review")
@Getter
@Setter
public class Review extends AbstractModel {

    protected Long movieId;
    protected String scoreOri;
    protected String quote;
    protected String url;
    @JsonDeserialize(using = DeSerializeZonedDateTime.class)
    protected ZonedDateTime creationDate;
    protected boolean topCritic;
    protected String score;
    protected String category;

    @JsonApiToOne
    protected Critic critic;

    @JsonApiToOne
    protected Publication publication;

}
