package com.rottentomatoes.movieapi.domain.model;

import io.katharsis.resource.annotations.JsonApiResource;
import lombok.Getter;
import lombok.Setter;

@JsonApiResource(type = "audienceSummary")
@Getter
@Setter
public class AudienceSummary extends AbstractModel {
    protected Integer scoreCount;
    protected Double avgScore;
    protected Integer wtsCount;
    protected Integer notInterestedCount;
    protected Integer reviewsCount;
}


