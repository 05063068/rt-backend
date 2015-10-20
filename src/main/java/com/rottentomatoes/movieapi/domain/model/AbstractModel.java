package com.rottentomatoes.movieapi.domain.model;

import io.katharsis.resource.annotations.JsonApiId;
import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter
public class AbstractModel {
    @JsonApiId
    protected long id;
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractModel that = (AbstractModel) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Long.hashCode(id);
    }
}
