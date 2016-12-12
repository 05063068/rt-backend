package com.rottentomatoes.movieapi.domain.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.katharsis.resource.annotations.JsonApiLazy;
import io.katharsis.resource.annotations.JsonApiLookupIncludeAutomatically;
import io.katharsis.resource.annotations.JsonApiResource;
import io.katharsis.resource.annotations.JsonApiToOne;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@JsonApiResource(type = "personSupplementaryInfo")
@Getter
@Setter
public class PersonSupplementaryInfo extends AbstractModel {

    protected String summaryBiography;
    protected String longBiography;
    @JsonDeserialize(using = DeSerializeZonedDateTime.class)
    protected ZonedDateTime dateOfBirth;
    protected String placeOfBirth;

}
