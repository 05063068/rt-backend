package com.rottentomatoes.movieapi.domain.model;


import com.flixster.image.Environment;
import com.flixster.image.IdGenerator;
import com.flixster.image.IdGenerator.IdGeneratorBuilder;
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
    	if(imageString == null){
    		return null;
    	}
        switch (imageString.toLowerCase()) {        	
            case "mv":
                return ImageType.MOVIE;
            case "cr":
                return ImageType.CRITIC;
            case "ac":
                return ImageType.ACTOR;
            case "pi":
            	return ImageType.PHOTO;
            case "fr":
            case "nn":
                return ImageType.MULTIUSE;
            default:
                return null;
        }
    }

    private ImageFormat getImageFormat(String imageFormatString) {
    	if(imageFormatString == null){
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

    public Image(String id, Integer originalHeight, Integer originalWidth, String format, String mediaType) {
        if(id == null || mediaType == null){
        	throw new RuntimeException("Image Id and MediaType cannot be null");
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
        if(format != null){
        	ImageFormat imageFormat = getImageFormat(format);
        	builder.format(imageFormat);
        }
        
        // original Dimensions may not be available
        if(originalWidth != null && originalHeight != null){
        	builder.originalSize(new Dimension(originalWidth, originalHeight));
        }
            
        String thumborId = builder.build().getEncodedId();
        
        this.setId(id);
        this.setThumborId(thumborId);
    }
}
