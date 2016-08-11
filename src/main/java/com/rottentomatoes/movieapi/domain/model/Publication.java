package com.rottentomatoes.movieapi.domain.model;

import io.katharsis.resource.annotations.JsonApiResource;
import lombok.Getter;
import lombok.Setter;

@JsonApiResource(type = "publication")
@Getter 
@Setter
public class Publication extends AbstractModel {

    protected String name;
    protected String country;

    // TODO: We should decide which of url or editorialUrl to use and abstract away the distinction so it is plug-and-play for the client
    protected String url;
    protected String editorialUrl;

}
