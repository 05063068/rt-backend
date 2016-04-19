package com.rottentomatoes.movieapi.domain.model;


import com.flixster.image.Environment;
import com.flixster.image.IdGenerator;
import com.flixster.image.ImageFormat;
import com.flixster.image.ImageType;
import io.katharsis.resource.annotations.JsonApiResource;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.time.DateUtils;

import java.awt.*;
import java.util.Date;

@JsonApiResource(type = "image")
@Getter
@Setter
public class Image extends AbstractModel {
    protected String thumborId;

    private ImageType getImageType(String imageString) {
        switch (imageString.toLowerCase()) {
            case "mv":
                return ImageType.MOVIE;
            case "cr":
                return ImageType.CRITIC;
            case "ac":
                return ImageType.ACTOR;
            case "fr":
            case "nn":
                return ImageType.MULTIUSE;
            default:
                return null;
        }
    }

    private ImageFormat getImageFormat(String imageFormatString) {
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

    public Image(String id, Integer originalHeight, Integer originalWidth, String format) {
        this(id, originalHeight, originalWidth, format, "MV");
    }

    public Image(String id, Integer originalHeight, Integer originalWidth, String format, String mediaType) {
        final int EXPIRE_DAYS = 45;
        final int MAX_WIDTH = 1200;
        ImageType type = getImageType(mediaType);
        Date expiry = DateUtils.addDays(new Date(), EXPIRE_DAYS);
        Environment environment = Environment.PROD;
        ImageFormat imageFormat = getImageFormat(format);
        int width = originalWidth;
        int height = originalHeight;

        String thumborId = IdGenerator.builder()
                .id(Long.valueOf(id))
                .type(type)
                .expiry(expiry)
                .environment(environment)
                .maxWidth(MAX_WIDTH)
                .format(imageFormat)
                .originalSize(new Dimension(width, height))
                .build().getEncodedId();

        this.setThumborId(thumborId);
    }
}
