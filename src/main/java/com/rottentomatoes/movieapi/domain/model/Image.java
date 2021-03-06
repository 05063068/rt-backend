package com.rottentomatoes.movieapi.domain.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.flixster.image.Environment;
import com.flixster.image.IdGenerator;
import com.flixster.image.IdGenerator.IdGeneratorBuilder;
import com.flixster.image.ImageFormat;
import com.flixster.image.ImageType;

import io.katharsis.resource.annotations.JsonApiResource;

import org.apache.commons.lang3.time.DateUtils;

import java.awt.*;
import java.util.Date;

import lombok.Getter;
import lombok.AccessLevel;
import lombok.Setter;

@JsonApiResource(type = "image")
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class Image extends AbstractModel {
    protected String thumborId;
    protected Integer height;
    protected Integer width;
    protected String format;
    protected String caption;
    
    public Image() {
        
    }

    public ImageType getImageType(String typeString) {

        for (ImageType t : ImageType.values()) {
            if (t.getCode().equals(typeString)) {
                return t;
            }
        }
        return null;
    }

    private ImageFormat getImageFormat(String imageFormatString) {
        if (imageFormatString == null) {
            return null;
        }
        switch (imageFormatString.toLowerCase()) {
            case "jpg":
                return ImageFormat.JPG;
            case "png":
                return ImageFormat.PNG;
            case "gif":
                return ImageFormat.GIF;
            default:
                return null;
        }
    }

    public Image(String id, Integer originalHeight, Integer originalWidth, String format, String mediaType, String caption) {
        if (id == null || mediaType == null) {
            throw new RuntimeException("Neither Image Id nor Media type can be null.");
        }

        final int EXPIRE_DAYS = 45;
        final int MAX_WIDTH = 1200;
        ImageType type = getImageType(mediaType);
        Date expiry = DateUtils.addDays(new Date(), EXPIRE_DAYS);
        Environment environment = Environment.PROD;


        IdGeneratorBuilder builder = IdGenerator.builder()
                .id(Long.valueOf(id))
                .type(type)
                .expiry(expiry)
                .environment(environment)
                .maxWidth(MAX_WIDTH);

        // format could be null
        if (format != null) {
            ImageFormat imageFormat = getImageFormat(format);
            builder.format(imageFormat);
        }

        // original Dimensions may not be available
        if (originalWidth != null && originalHeight != null) {
            builder.originalSize(new Dimension(originalWidth, originalHeight));
        }

        String thumborId = builder.build().getEncodedId();

        this.id = type.getCode() + "-" + id;
        this.thumborId = thumborId;
        this.height = originalHeight;
        this.width = originalWidth;
        this.format = format;
        this.caption = caption;
    }

}
