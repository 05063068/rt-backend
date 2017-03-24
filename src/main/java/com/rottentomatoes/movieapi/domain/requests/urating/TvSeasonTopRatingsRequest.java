/**
 * Flixster Inc. Copyright (c) 2017. All Rights Reserved.
 */

package com.rottentomatoes.movieapi.domain.requests.urating;

import java.util.Map;

import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;

import io.katharsis.queryParams.RequestParams;

public class TvSeasonTopRatingsRequest extends AbstractUserRatingsRequest {
    public static final String API_STAT_NAME = "TV_SEASON_TOP_RATINGS";

    private static final String RELATIVE_URL_TV_SERIES_ID_PLACEHOLDER = "{series-id}";
    private static final String RELATIVE_URL_TV_SEASON_ID_PLACEHOLDER = "{season-id}";
    private static final String RELATIVE_URL = "/tv/v1/series/"
            + RELATIVE_URL_TV_SERIES_ID_PLACEHOLDER + "/season/"
            + RELATIVE_URL_TV_SEASON_ID_PLACEHOLDER + "/ratings/top";

    public TvSeasonTopRatingsRequest(final Environment environment,
            final RequestParams requestParams, final String seasonId) {
        super(environment, HttpMethod.GET,
                prepareRelativeUrl(getTvSeriesId(requestParams), seasonId), null, null,
                API_STAT_NAME);
    }

    private static String getTvSeriesId(final RequestParams requestParams) {
        Map<String, Object> filters = requestParams.getFilters();
        Object tvSeriesId = filters.get("tvSeriesId");
        return tvSeriesId == null ? null : tvSeriesId.toString();
    }

    private static final String prepareRelativeUrl(String seriesId, String seasonId) {
        return RELATIVE_URL.replace(RELATIVE_URL_TV_SERIES_ID_PLACEHOLDER, seriesId)
                .replace(RELATIVE_URL_TV_SEASON_ID_PLACEHOLDER, seasonId);
    }
}
