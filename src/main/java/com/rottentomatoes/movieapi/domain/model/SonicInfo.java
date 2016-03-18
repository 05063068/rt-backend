package com.rottentomatoes.movieapi.domain.model;

import io.katharsis.resource.annotations.JsonApiResource;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@JsonApiResource(type = "sonicInfo")
@Getter
@Setter
public class SonicInfo extends AbstractModel {

    protected String purchaseType;
    protected Float price;
    protected LocalDate startDate;
    protected LocalDate endDate;
    protected Integer skuReadyInd;

}
