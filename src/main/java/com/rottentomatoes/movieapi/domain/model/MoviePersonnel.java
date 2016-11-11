package com.rottentomatoes.movieapi.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.katharsis.resource.annotations.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@JsonApiResource(type = "moviePersonnel")
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class MoviePersonnel extends AbstractModel {

    public MoviePersonnel(){
        // Preinitialize lists
        actors = new ArrayList<MovieCast>();
        producers = new ArrayList<MovieCast>();
        directors =  new ArrayList<MovieCast>();
        executiveProducers = new ArrayList<MovieCast>();
        screenwriters = new ArrayList<MovieCast>();
    }

    @JsonApiToMany
    protected List<MovieCast> actors;

    @JsonApiToMany
    protected List<MovieCast> producers;

    @JsonApiToMany
    protected List<MovieCast> directors;

    @JsonApiToMany
    protected List<MovieCast> executiveProducers;

    @JsonApiToMany
    protected List<MovieCast> screenwriters;

}
