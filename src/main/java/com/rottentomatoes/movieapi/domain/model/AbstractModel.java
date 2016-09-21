package com.rottentomatoes.movieapi.domain.model;

import io.katharsis.resource.annotations.JsonApiId;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class AbstractModel implements Serializable {
    @JsonApiId
    protected String id;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractModel that = (AbstractModel) o;
        if(id != null) {
            return id.equals(that.id);
        }
        else{
            return super.equals(this);
        }

    }

    @Override
    public int hashCode() {
        if(id!=null)
            return id.hashCode();
        else{
            return super.hashCode();
        }
    }
}
