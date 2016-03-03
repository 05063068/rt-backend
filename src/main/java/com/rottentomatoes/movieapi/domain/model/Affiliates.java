package com.rottentomatoes.movieapi.domain.model;

import io.katharsis.resource.annotations.JsonApiLookupIncludeAutomatically;
import io.katharsis.resource.annotations.JsonApiResource;
import io.katharsis.resource.annotations.JsonApiToMany;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@JsonApiResource(type = "affiliates")
@Getter
@Setter
public class Affiliates extends AbstractModel {

    @JsonApiToMany
    @JsonApiLookupIncludeAutomatically
    protected Iterable<AmazonInfo> amazonInfo;

    @JsonApiToMany
    @JsonApiLookupIncludeAutomatically
    protected Iterable<ItunesInfo> itunesInfo;

    @JsonApiToMany
    @JsonApiLookupIncludeAutomatically
    protected Iterable<SonicInfo> sonicInfo;

    @JsonApiToMany
    @JsonApiLookupIncludeAutomatically
    protected Iterable<VuduInfo> vuduInfo;

}

