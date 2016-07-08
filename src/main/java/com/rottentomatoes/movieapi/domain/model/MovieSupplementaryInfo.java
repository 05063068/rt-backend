package com.rottentomatoes.movieapi.domain.model;

import java.util.ArrayList;
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
    public MovieSupplementaryInfo(String id, String dvdWindow, String openingWindow){
        this.id = id;
        windows = new ArrayList<String>();
        if(dvdWindow != null) {
            windows.add(dvdWindow);
        }
        if(openingWindow != null) {
            windows.add(openingWindow);
        }
    }

    protected String synopsis;
    protected String officialUrl;
    protected String studioName;
    protected String releaseScope;
    protected Integer runningTime;
    protected Integer boxOffice;
    protected Integer cumulativeBoxOffice;

    protected Map<String, Object> releaseDates;

    protected List<String> windows;

    // attribute level associations (not relationships)
    protected Image posterImage;
    protected Image heroImage;
    protected VideoClip mainTrailer;

}
