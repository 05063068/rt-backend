package com.rottentomatoes.movieapi.domain.model;

import lombok.Getter;
import lombok.Setter;

/* NOTE: myBatis ONLY model. Not exposed via Katharsis. No Katharsis annotations needed. */
@Getter
@Setter
public class Character {
    protected String name;
}