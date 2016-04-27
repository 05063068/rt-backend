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
import java.util.HashMap;
import java.util.Map;

@JsonApiResource(type = "image")
@Getter
@Setter
public class Image extends AbstractModel {
    protected String thumborId;
    
    protected Integer height;
    protected Integer width;
    protected String format;
    protected String type;

    private static Map<String,ImageType> imageTypeEnumMap = null;
    
    private synchronized ImageType getImageType(String typeString) {    	
    	if(imageTypeEnumMap == null){
    		imageTypeEnumMap = new HashMap<>();
    		for(ImageType t: ImageType.values()){
    			imageTypeEnumMap.put(t.getCode(), t);
    		}
    	}    	
    	return imageTypeEnumMap.get(typeString);
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
        
        this.setId(type.getCode()+"-"+id);
        this.setThumborId(thumborId);
        this.setHeight(originalHeight);
        this.setWidth(originalWidth);
        this.setFormat(format);
        this.setType(mediaType);
    }
}
