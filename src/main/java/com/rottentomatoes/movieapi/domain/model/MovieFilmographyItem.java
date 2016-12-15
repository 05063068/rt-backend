
package com.rottentomatoes.movieapi.domain.model;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.rottentomatoes.movieapi.enums.MovieCastRole;
import io.katharsis.resource.annotations.JsonApiResource;
import io.katharsis.resource.annotations.JsonApiToOne;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@JsonApiResource(type = "movieFilmographyItem")
@Getter
@Setter
public class MovieFilmographyItem extends AbstractModel {

    protected Iterable<String> roles;

    protected Iterable<String> characters;

    @JsonApiToOne
    protected Movie movie;

    @JsonSetter(value = "roles")
    public void setRoles(final Iterable<String> roles) {
        this.roles = new ArrayList<>();
        roles.forEach(item -> ((ArrayList) this.roles).add(MovieCastRole.getNameByCode(item)));
    }
}
