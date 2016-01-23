package com.rottentomatoes.movieapi.domain.model;

import java.util.Date;

import io.katharsis.resource.annotations.JsonApiResource;
import lombok.Getter;
import lombok.Setter;

@JsonApiResource(type = "sonicInfo")
@Getter
@Setter
public class SonicInfo extends AbstractModel {

    protected String purchaseType;
    protected Float price;
    protected Date startDate;
    protected Date endDate;
    protected Integer skuReadyInd;

}
