package com.rottentomatoes.movieapi.domain.model;

import io.katharsis.resource.annotations.JsonApiResource;
import io.katharsis.resource.annotations.JsonApiToOne;
import lombok.Getter;
import lombok.Setter;

@JsonApiResource(type = "review")
@Getter 
@Setter
public class Review extends AbstractModel{

    protected Long movie_id;

    protected String quote;
    
    @JsonApiToOne
    protected Critic critic;
    
    @JsonApiToOne
    protected Publication publication;

}
