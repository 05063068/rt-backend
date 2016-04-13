package com.rottentomatoes.movieapi.domain.model;

import java.util.Map;

import io.katharsis.resource.annotations.JsonApiResource;
import lombok.Getter;
import lombok.Setter;

@JsonApiResource(type = "criticSummary")
@Getter
@Setter
public class CriticSummary extends AbstractModel {

    protected String consensus;

    protected Map<String, Object> allCritics;
    protected Map<String, Object> topCritics;
}
