package com.rottentomatoes.movieapi.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.katharsis.resource.annotations.JsonApiLazy;
import io.katharsis.resource.annotations.JsonApiLookupIncludeAutomatically;
import io.katharsis.resource.annotations.JsonApiResource;
import io.katharsis.resource.annotations.JsonApiToOne;
import io.katharsis.resource.annotations.JsonApiToMany;
import io.katharsis.response.MetaDataEnabledList;
import lombok.Getter;
import lombok.Setter;

@JsonApiResource(type = "critic")
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class Critic extends AbstractModel {

    protected String name;
    protected Image mainImage;
    protected String vanity;
    protected String status;
    protected boolean tmApproved;
    protected boolean topCritic;
    protected Integer agreePercent;

    @JsonApiToOne
    @JsonApiLazy
    @JsonApiLookupIncludeAutomatically
    protected CriticSupplementaryInfo criticSupplementaryInfo;

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
    protected Iterable<Publication> affiliatedPublications;

    @JsonApiToMany
    @JsonApiLazy
    @JsonApiLookupIncludeAutomatically
    protected Iterable<CriticGroup> affiliatedGroups;

    public void setAgreePercent(Integer percentage) {
        agreePercent = percentage;
    }
}