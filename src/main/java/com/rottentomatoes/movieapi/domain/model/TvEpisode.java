
package com.rottentomatoes.movieapi.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.katharsis.resource.annotations.JsonApiLazy;
import io.katharsis.resource.annotations.JsonApiLookupIncludeAutomatically;
import io.katharsis.resource.annotations.JsonApiResource;
import io.katharsis.resource.annotations.JsonApiToMany;
import io.katharsis.resource.annotations.JsonApiToOne;
import io.katharsis.response.MetaDataEnabledList;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.Map;

@JsonApiResource(type = "tvEpisode")
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class TvEpisode extends AbstractModel {
    protected String title;
    protected String vanityUrl;

    protected int episodeNumber;
    protected String tvSeasonId;
    protected String network;
    protected String genre;
    protected String synopsis;
    @JsonDeserialize(using = DeSerializeZonedDateTime.class)
    protected ZonedDateTime airDate;
    protected int runningTime;

    // complex (nested) attributes
    protected Map<String, Object> tomatometer;
    protected Map<String, Object> posterImage;
    protected Map<String, Object> heroImage;
    protected Map<String, Object> mainTrailer;

    @JsonApiToOne
    @JsonApiLazy
    @JsonApiLookupIncludeAutomatically
    protected ReviewInfo reviewInfo;

    @JsonApiToOne
    @JsonApiLazy
    @JsonApiLookupIncludeAutomatically
    protected Personnel tvPersonnel;

    @JsonApiToMany
    @JsonApiLazy
    @JsonApiLookupIncludeAutomatically
    protected MetaDataEnabledList<VideoClip> videoClips;

    @JsonApiToMany
    @JsonApiLazy
    @JsonApiLookupIncludeAutomatically
    protected Iterable<Image> images;

}
