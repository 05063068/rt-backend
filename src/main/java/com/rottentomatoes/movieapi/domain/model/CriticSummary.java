package com.rottentomatoes.movieapi.domain.model;

import io.katharsis.resource.annotations.JsonApiResource;
import lombok.Getter;
import lombok.Setter;

@JsonApiResource(type = "criticSummary")
@Getter
@Setter
public class CriticSummary extends AbstractModel {

    protected Integer reviewCount;
    protected Integer averageScore;
    protected String consensus;
    protected Integer isCertifiedFresh;
    protected Integer isPendingCertifiedFresh;
    protected Integer freshCount;
    protected Integer rottenCount;
    protected Integer topCriticsCount;
    protected Integer allCriticsCount;
    protected Integer dvdCount;
    protected Integer topFreshCount;
    protected Integer topRottenCount;
    protected Integer topDvdCount;
    protected Integer topCountryFreshCount;
    protected Integer topCountryRottenCount;
    protected Integer topCountryDvdCount;
}
