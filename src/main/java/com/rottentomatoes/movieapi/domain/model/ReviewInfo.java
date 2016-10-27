package com.rottentomatoes.movieapi.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.katharsis.resource.annotations.JsonApiResource;
import io.katharsis.resource.annotations.JsonApiToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@JsonApiResource(type = "reviewInfo")
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class ReviewInfo extends AbstractModel {

    protected int topCriticsCount;
    protected int freshCount;
    protected int rottenCount;
    protected int totalCount;
    protected Iterable<Review> reviews;

    public ReviewInfo(Map counts, Iterable<Review> reviewList) {
        this.topCriticsCount = (int) counts.get(" Top Critics");
        this.freshCount = (int) counts.get("Fresh");
        this.rottenCount = (int) counts.get("Rotten");
        this.totalCount = (int) counts.get("All Critics");
        this.reviews = reviewList;
    }
}
