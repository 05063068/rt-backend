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

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rottentomatoes.movieapi.domain.model.MovieCast;
import com.rottentomatoes.movieapi.enums.MpaaRating;

import io.katharsis.resource.annotations.JsonApiResource;
import io.katharsis.resource.annotations.JsonApiToMany;
import lombok.Getter;
import lombok.Setter;

@JsonApiResource(type = "movie")
@Getter 
@Setter
public class Movie extends AbstractModel  {
    
    protected String title;
    protected Integer year;
    protected Date theaterReleaseDate;
    protected Date dvdReleaseDate;
    protected Integer tomatometer;
    protected Integer boxOffice;
    protected MpaaRating mpaaRating;
    protected String studio;
    protected Date creationDate;    
    protected Date lastModifiedDate;    
    
    @JsonApiToMany(lazy=true)
    protected Iterable<MovieCast> movieCast;
    
    @JsonApiToMany(lazy=true)
    protected Iterable<Review> reviews;
    
    @JsonApiToMany(lazy=true)
    protected Iterable<VideoClip> videoClips;
    
    @JsonApiToMany(lazy=true)
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
