package com.rottentomatoes.movieapi.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.katharsis.resource.annotations.JsonApiResource;
import io.katharsis.resource.annotations.JsonApiToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
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
    protected int allCriticsCount;
    protected Iterable<Review> reviews;

    public ReviewInfo(HashMap<String, Object> counts, Iterable<Review> reviewList) {
        if (counts.containsKey("topCriticsCount")) {
            this.topCriticsCount = (int) counts.get("topCriticsCount");
        }
        if (counts.containsKey("freshCount")) {
            this.freshCount = (int) counts.get("freshCount");
        }
        if (counts.containsKey("rottenCount")) {
            this.rottenCount = (int) counts.get("rottenCount");
        }
        if (counts.containsKey("allCriticsCount")) {
            this.allCriticsCount = (int) counts.get("allCriticsCount");
        }
        this.reviews = reviewList;
    }
}
