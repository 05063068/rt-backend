package com.rottentomatoes.movieapi.domain.model;

import com.rottentomatoes.movieapi.domain.model.AbstractModel;
import com.sun.org.apache.xpath.internal.operations.String;
import io.katharsis.resource.annotations.JsonApiLookupIncludeAutomatically;
import io.katharsis.resource.annotations.JsonApiResource;
import io.katharsis.resource.annotations.JsonApiToMany;
import lombok.Getter;
import lombok.Setter;

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

