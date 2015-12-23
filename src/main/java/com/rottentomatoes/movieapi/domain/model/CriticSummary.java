package com.rottentomatoes.movieapi.domain.model;

import io.katharsis.resource.annotations.JsonApiResource;
import lombok.Getter;
import lombok.Setter;

@JsonApiResource(type = "criticSummary")
@Getter
@Setter
public class CriticSummary extends AbstractModel {

    protected Integer criticReviewCount;
    protected Integer criticAverageScore;
    protected String criticConsensus;
    protected Integer criticCertifiedFresh;
    protected Integer criticPendingCertifiedFresh;

}
