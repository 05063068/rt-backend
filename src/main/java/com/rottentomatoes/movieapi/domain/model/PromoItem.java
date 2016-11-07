package com.rottentomatoes.movieapi.domain.model;

import io.katharsis.resource.annotations.JsonApiResource;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonApiResource(type = "promoItem")
@Getter
@Setter
public class PromoItem extends AbstractModel {
    protected String promoSection;
    protected Integer sequence;
    @JsonDeserialize(using = DeSerializeZonedDateTime.class)
    protected ZonedDateTime startDate;
    @JsonDeserialize(using = DeSerializeZonedDateTime.class)
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
