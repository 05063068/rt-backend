package com.rottentomatoes.movieapi.domain.model;

import io.katharsis.resource.annotations.JsonApiResource;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@JsonApiResource(type = "vuduInfo")
@Getter
@Setter
public class VuduInfo extends AbstractModel {

    protected String format;
    protected String type;
    protected Float price;
    protected String currency;
    protected Date startDate;
    protected Date endDate;

}
