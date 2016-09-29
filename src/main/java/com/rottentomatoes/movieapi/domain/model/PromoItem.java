package com.rottentomatoes.movieapi.domain.model;

import io.katharsis.resource.annotations.JsonApiResource;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@JsonApiResource(type = "promoItem")
@Getter
@Setter
public class PromoItem extends AbstractModel {
    protected String promoSection;
    protected Integer sequence;
    protected ZonedDateTime startDate;
    protected ZonedDateTime startTime;
    protected String header;
    protected String body;
    protected String url;
    protected String objectId;
    protected String objectType;
    protected String subHeader;
    protected String siteImageId;
    private Image image;
 }
