package com.rottentomatoes.movieapi.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.katharsis.resource.annotations.JsonApiResource;
import lombok.Getter;
import lombok.Setter;

@JsonApiResource(type = "tv-vanity-token")
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class TvVanityToken extends AbstractModel {
    protected String tvSeriesId;
    protected String tvSeasonId;
    protected String tvEpisodeId;

    public TvVanityToken(String token, String seriesId, String seasonId, String episodeId) {
        this.id = token;
        this.tvSeriesId = seriesId;
        this.tvSeasonId = seasonId;
        this.tvEpisodeId = episodeId;
    }
}
