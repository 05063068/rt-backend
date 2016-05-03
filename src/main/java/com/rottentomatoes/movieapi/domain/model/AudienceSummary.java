package com.rottentomatoes.movieapi.domain.model;

import java.util.Map;

import io.katharsis.resource.annotations.JsonApiResource;
import lombok.Getter;
import lombok.Setter;

@JsonApiResource(type = "audienceSummary")
@Getter
@Setter
public class AudienceSummary extends AbstractModel {
	protected Map<String, Object> popcornMeter;
	
    protected Integer audienceCount;
    protected Double avgScore;
}


