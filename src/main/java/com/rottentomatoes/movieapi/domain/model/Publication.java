package com.rottentomatoes.movieapi.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.katharsis.resource.annotations.JsonApiLazy;
import io.katharsis.resource.annotations.JsonApiLookupIncludeAutomatically;
import io.katharsis.resource.annotations.JsonApiResource;
import io.katharsis.resource.annotations.JsonApiToMany;
import lombok.Getter;
import lombok.Setter;

@JsonApiResource(type = "publication")
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter 
@Setter
public class Publication extends AbstractModel {

    protected String name;
    protected String country;
    protected boolean tomatometerApproved;
    protected boolean topPublication;

    // TODO: We should decide which of url or editorialUrl to use and abstract away the distinction so it is plug-and-play for the client
    protected String url;
    protected String editorialUrl;

    @JsonApiToMany
    @JsonApiLazy
    @JsonApiLookupIncludeAutomatically
    protected Iterable<Review> reviews;

    @JsonApiToMany
    @JsonApiLazy
    @JsonApiLookupIncludeAutomatically
    protected Iterable<Review> tvReviews;

    @JsonApiToMany
    @JsonApiLazy
    @JsonApiLookupIncludeAutomatically
    protected Iterable<Critic> affiliatedCritics;

}
