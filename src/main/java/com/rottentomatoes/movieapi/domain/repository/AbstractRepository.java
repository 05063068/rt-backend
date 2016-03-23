package com.rottentomatoes.movieapi.domain.repository;

import com.flixster.image.Environment;
import com.flixster.image.IdGenerator;
import com.flixster.image.ImageFormat;
import com.flixster.image.ImageType;
import io.katharsis.queryParams.PaginationKeys;
import io.katharsis.queryParams.RequestParams;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.io.Serializable;
import java.util.Date;

@Component
public class AbstractRepository implements Serializable {
    @Autowired
    protected SqlSession sqlSession;

    String LIMIT = "Limit";
    Integer DEFAULT_LIMIT = 10;
    Integer DEFAULT_OFFSET = 0;

    protected String getThumborId(Long id, ImageType type, Date expiry, Environment environment, ImageFormat format, int width, int height) {
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

    protected ImageType getImageType(String imageString) {
        if (imageString.equalsIgnoreCase("mv")) {
            return ImageType.MOVIE;
        }
        return null;
    }

    protected ImageFormat getImageFormat(String imageFormatString) {
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

    protected Integer getLimit(String fieldName, RequestParams requestParams) {
        if (requestParams != null) {
            if (requestParams.getPagination() != null && requestParams.getPagination().containsKey(PaginationKeys.limit)) {
                return requestParams.getPagination().get(PaginationKeys.limit);
            } else if (requestParams.getFilters() != null && requestParams.getFilters().containsKey(fieldName + LIMIT)) {
                return (Integer) requestParams.getFilters().get(fieldName + LIMIT);
            }
        }
        return DEFAULT_LIMIT;

    }

    protected Integer getOffset(String fieldName, RequestParams requestParams) {
        if (requestParams != null) {
            if (requestParams.getPagination() != null && requestParams.getPagination().containsKey(PaginationKeys.offset)) {
                return requestParams.getPagination().get(PaginationKeys.offset);
            }
        }
        return DEFAULT_OFFSET;

    }
}
