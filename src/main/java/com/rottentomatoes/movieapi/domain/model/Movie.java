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

import java.util.Date;
import java.util.Map;

import com.rottentomatoes.movieapi.enums.MpaaRating;

import io.katharsis.resource.annotations.JsonApiLazy;
import io.katharsis.resource.annotations.JsonApiResource;
import io.katharsis.resource.annotations.JsonApiToMany;
import io.katharsis.resource.annotations.JsonApiToOne;
import lombok.Getter;
import lombok.Setter;

@JsonApiResource(type = "movie")
@Getter 
@Setter
public class Movie extends AbstractModel  {

    protected String title;
    protected Integer year;
    
    protected Integer boxOffice;
    protected MpaaRating mpaaRating;
    protected String studioName;
    protected Date creationDate;
    protected Date lastModifiedDate;
    protected String advisory;
    protected Integer status;
    protected String vanityToken;
    protected String synopsis;
    protected Integer runningTime;
    protected String officialUrl;
    protected String shopLink;

    protected Map<String, Object> tomatometer;
    protected Map<String, Object> releaseDates;
    
    
    @JsonApiToOne
    protected Image mainImage;

    @JsonApiToOne
    @JsonApiLazy
    protected CriticSummary criticSummary;

    @JsonApiToOne
    @JsonApiLazy
    protected Franchise franchise;

    @JsonApiToOne
    @JsonApiLazy
    protected AudienceSummary audienceSummary;
    
    
    @JsonApiToMany
    @JsonApiLazy
    protected Iterable<Genre> genres;
   
    @JsonApiToMany
    @JsonApiLazy
    protected Iterable<MovieCast> movieCast;

    @JsonApiToMany
    @JsonApiLazy
    protected Iterable<ItunesInfo> itunesInfo;
    
    @JsonApiToMany
    @JsonApiLazy
    protected Iterable<Review> reviews;
    
    @JsonApiToMany
    @JsonApiLazy
    protected Iterable<VideoClip> videoClips;

    @JsonApiToMany
    @JsonApiLazy
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
