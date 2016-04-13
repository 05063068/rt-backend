package com.rottentomatoes.movieapi.domain.model;

import io.katharsis.resource.annotations.JsonApiResource;
import lombok.Getter;
import lombok.Setter;

@JsonApiResource(type = "criticSummary")
@Getter
@Setter
public class CriticSummary extends AbstractModel {

    protected Integer averageScore;
    protected String consensus;
    protected Integer isCertifiedFresh;
    protected Integer isPendingCertifiedFresh;
    
    protected Integer allCriticsCount;
    protected Integer allFreshCount;
    protected Integer allRottenCount;
    protected Integer allDvdCount;
    
    protected Integer topCriticsCount;
    protected Integer topFreshCount;
    protected Integer topRottenCount;
    protected Integer topDvdCount;    
}
