package com.rottentomatoes.movieapi.domain.model;

import io.katharsis.resource.annotations.JsonApiResource;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@JsonApiResource(type = "audienceSummary")
@Getter
@Setter
public class AudienceSummary extends AbstractModel {

    protected Integer audienceScoreCount;
    protected Double audienceAverageScore;
    protected Integer audienceWTSCount;
    protected Integer audienceNotInterestedCount;
    protected Integer audienceReviewsCount;
}


