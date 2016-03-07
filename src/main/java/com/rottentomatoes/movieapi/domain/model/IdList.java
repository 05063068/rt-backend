package com.rottentomatoes.movieapi.domain.model;

import io.katharsis.resource.annotations.JsonApiResource;
import io.katharsis.resource.annotations.JsonApiToMany;
import lombok.Getter;
import lombok.Setter;

@JsonApiResource(type = "id-list")
@Getter 
@Setter
public class IdList extends AbstractModel {

    @JsonApiToMany
    protected Iterable<String> idsInList;

}
