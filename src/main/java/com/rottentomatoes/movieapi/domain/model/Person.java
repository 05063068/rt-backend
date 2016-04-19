package com.rottentomatoes.movieapi.domain.model;

import io.katharsis.resource.annotations.JsonApiResource;
import io.katharsis.resource.annotations.JsonApiToOne;
import lombok.Getter;
import lombok.Setter;

@JsonApiResource(type = "person")
@Getter
@Setter
public class Person extends AbstractModel {
    protected String vanity;
    protected String name;
    protected Image mainImage;

    public Person(String imageId, Integer originalHeight, Integer originalWidth, String format) {
        if (imageId != null) {
            this.mainImage = new Image(imageId, originalHeight, originalWidth, format, "AC");
        }
    }
}