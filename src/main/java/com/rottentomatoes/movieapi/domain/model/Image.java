package com.rottentomatoes.movieapi.domain.model;


import com.flixster.image.Environment;
import com.flixster.image.IdGenerator;
import com.flixster.image.ImageFormat;
import com.flixster.image.ImageType;
import io.katharsis.resource.annotations.JsonApiResource;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.util.Calendar;
import java.util.Date;

@JsonApiResource(type = "image")
@Getter
@Setter
public class Image extends AbstractModel {

    private String getThumborId(Long id, ImageType type, Date expiry, Environment environment, ImageFormat format, int width, int height) {
        final int MAX_WIDTH = 1200;
        return IdGenerator.builder()
                .id(id)
                .type(type)
                .expiry(expiry)
                .environment(environment)
                .maxWidth(MAX_WIDTH)
                .format(format)
                .originalSize(new Dimension(width, height))
                .build().getEncodedId();
    }

    private ImageType getImageType(String imageString) {
        if (imageString.equalsIgnoreCase("mv")) {
            return ImageType.MOVIE;
        }
        if (imageString.equalsIgnoreCase("cr")) {
            return ImageType.CRITIC;
        }
        if (imageString.equalsIgnoreCase("ac")) {
            return ImageType.ACTOR;
        }
        if (imageString.equalsIgnoreCase("fr") || imageString.equalsIgnoreCase("nn")) {
            return ImageType.MULTIUSE;
        }
        return null;
    }

    private ImageFormat getImageFormat(String imageFormatString) {
        if (imageFormatString.equalsIgnoreCase("jpg")) {
            return ImageFormat.JPG;
        }
        if (imageFormatString.equalsIgnoreCase("png")) {
            return ImageFormat.PNG;
        }
        if (imageFormatString.equalsIgnoreCase("gif")) {
            return ImageFormat.GIF;
        }
        return null;
    }

    public Image (String id, Integer originalHeight, Integer originalWidth, String format) {
        this(id, originalHeight, originalWidth, format, "MV");
    }

    public Image (String id, Integer originalHeight, Integer originalWidth, String format, String mediaType) {
        ImageType type = getImageType(mediaType);
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, 45);
        Date expiry = c.getTime();
        Environment environment = Environment.PROD;
        ImageFormat imageFormat = getImageFormat(format);
        int width = originalWidth;
        int height = originalHeight;
        this.setThumborId(getThumborId(Long.parseLong(id), type, expiry, environment, imageFormat, width, height));
    }

    protected String thumborId;
}
