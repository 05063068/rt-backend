package com.rottentomatoes.movieapi.domain.model;

import io.katharsis.resource.annotations.JsonApiResource;
import lombok.Getter;
import lombok.Setter;

@JsonApiResource(type = "amazonInfo")
@Getter
@Setter
public class AmazonInfo extends AbstractModel {

    protected String format;
    protected String purchaseType;
    protected Float price;
    protected Integer primeInd;

}
