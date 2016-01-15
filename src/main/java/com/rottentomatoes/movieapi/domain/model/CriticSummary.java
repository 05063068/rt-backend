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
    protected Integer certifiedFresh;
    protected Integer pendingCertifiedFresh;

}
