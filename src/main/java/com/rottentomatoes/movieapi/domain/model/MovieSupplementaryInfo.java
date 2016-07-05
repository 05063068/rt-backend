package com.rottentomatoes.movieapi.domain.model;

import java.util.List;
import java.util.Map;

import io.katharsis.resource.annotations.JsonApiResource;
import io.katharsis.resource.annotations.JsonApiToOne;
import com.rottentomatoes.movieapi.domain.model.Person;
import lombok.Getter;
import lombok.Setter;

@JsonApiResource(type = "movieSupplementaryInfo")
@Getter
@Setter
public class MovieSupplementaryInfo extends AbstractModel {
    protected String synopsis;
    protected String officialUrl;
    protected String studioName;
    protected String openingWindow;
    protected String dvdWindow;
    protected String releaseScope;
    protected Integer runningTime;
    protected Integer boxOffice;
    protected Integer cummulativeBoxOffice;

    protected Map<String, Object> releaseDates;

    // attribute level associations (not relationships)
    protected Image posterImage;
    protected Image heroImage;
    protected VideoClip mainTrailer;

}
