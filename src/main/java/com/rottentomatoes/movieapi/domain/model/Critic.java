package com.rottentomatoes.movieapi.domain.model;

import io.katharsis.resource.annotations.JsonApiResource;
import lombok.Getter;
import lombok.Setter;

@JsonApiResource(type = "critic")
@Getter
@Setter
public class Critic extends AbstractModel {

    public Critic (String id, Integer originalHeight, Integer originalWidth, String format)  {
        this.image = new Image(id, originalHeight, originalWidth, format, "CR");
    }

    protected String name;
    protected Image image;
    protected String vanityUrl;
}