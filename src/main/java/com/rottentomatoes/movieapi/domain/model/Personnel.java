package com.rottentomatoes.movieapi.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.rottentomatoes.movieapi.enums.MovieCastRole;
import io.katharsis.resource.annotations.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@JsonApiResource(type = "moviePersonnel")
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class Personnel extends AbstractModel {

    public Personnel(){
        // Preinitialize lists
        actors = new ArrayList<MovieCast>();
        producers = new ArrayList<MovieCast>();
        directors =  new ArrayList<MovieCast>();
        executiveProducers = new ArrayList<MovieCast>();
        screenwriters = new ArrayList<MovieCast>();
        creators = new ArrayList<MovieCast>();
    }

    public Personnel(String id, List<MovieCast> castList) {
        this();
        this.setId(id);

        // Load Personnel object manually;
        if (castList != null && castList.size() > 0) {
            for (MovieCast item : castList) {
                if (MovieCastRole.isPerformer(item)) {
                    actors.add(item);
                } else if (MovieCastRole.isProducer(item)) {
                    producers.add(item);
                } else if (MovieCastRole.isDirector(item)) {
                    directors.add(item);
                } else if (MovieCastRole.isExecutiveProducer(item)) {
                    executiveProducers.add(item);
                } else if (MovieCastRole.isScreenwriter(item)) {
                    screenwriters.add(item);
                } else if (MovieCastRole.isCreator(item)) {
                    creators.add(item);
                }
            }
        }
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

    @JsonApiToMany
    protected List<MovieCast> creators;

}
