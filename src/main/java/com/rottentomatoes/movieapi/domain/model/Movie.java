/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.rottentomatoes.movieapi.domain.model;

import java.time.ZonedDateTime;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rottentomatoes.movieapi.enums.MpaaRating;

import io.katharsis.resource.annotations.JsonApiLazy;
import io.katharsis.resource.annotations.JsonApiLookupIncludeAutomatically;
import io.katharsis.resource.annotations.JsonApiResource;
import io.katharsis.resource.annotations.JsonApiToMany;
import io.katharsis.resource.annotations.JsonApiToOne;

import io.katharsis.response.MetaDataEnabledList;
import lombok.Getter;
import lombok.Setter;

@JsonApiResource(type = "movie")
@Getter
@Setter
public class Movie extends AbstractModel {

	// Atomic attributes
    protected String title;
    protected Integer year;
    protected Integer boxOffice;
    protected Integer cummulativeBoxOffice;
    protected MpaaRating mpaaRating;
    protected String studioName;
    protected ZonedDateTime creationDate;
    protected ZonedDateTime lastModifiedDate;
    protected String advisory;
    protected String vanity;
    protected String synopsis;
    protected Integer runningTime;
    protected String officialUrl;
    protected String openingWindow;
    protected String dvdWindow;
    protected String releaseScope;
    
    // complex (nested) attributes
    protected Map<String, Object> tomatometer;
    protected Map<String, Object> releaseDates;
    
    // attribute level associations (not relationships)
    protected Image posterImage;
    protected Image heroImage;
    protected VideoClip mainTrailer;

    // relationships
    @JsonApiToOne
    @JsonApiLazy
    @JsonApiLookupIncludeAutomatically
    protected CriticSummary criticSummary;

    @JsonApiToOne
    @JsonApiLazy
    @JsonApiLookupIncludeAutomatically
    protected Franchise franchise;

    @JsonApiToOne
    @JsonApiLazy
    @JsonApiLookupIncludeAutomatically
    protected Affiliates affiliates;

    @JsonApiToOne
    @JsonApiLazy
    @JsonApiLookupIncludeAutomatically
    protected AudienceSummary audienceSummary;

    @JsonApiToMany
    @JsonApiLazy
    @JsonApiLookupIncludeAutomatically
    protected Iterable<Genre> genres;

    @JsonApiToMany
    @JsonApiLazy
    @JsonApiLookupIncludeAutomatically
    protected Iterable<MovieCast> movieCast;

    @JsonApiToMany
    @JsonApiLazy
    @JsonApiLookupIncludeAutomatically
    protected Iterable<Review> reviews;

    @JsonApiToMany
    @JsonApiLazy
    @JsonApiLookupIncludeAutomatically
    protected MetaDataEnabledList<AudienceReview> audienceReviews;

    @JsonApiToMany
    @JsonApiLazy
    @JsonApiLookupIncludeAutomatically
    protected MetaDataEnabledList<VideoClip> videoClips;

    @JsonApiToMany
    @JsonApiLazy
    @JsonApiLookupIncludeAutomatically
    protected Iterable<Image> images;

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
