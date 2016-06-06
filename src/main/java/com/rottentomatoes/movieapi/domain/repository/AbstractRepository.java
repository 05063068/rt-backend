package com.rottentomatoes.movieapi.domain.repository;

import java.io.Serializable;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.katharsis.queryParams.PaginationKeys;
import io.katharsis.queryParams.RequestParams;

@Component
public class AbstractRepository implements Serializable {
    @Autowired
    protected SqlSession sqlSession;

    String LIMIT = "Limit";
    Integer DEFAULT_LIMIT = 10;
    Integer DEFAULT_OFFSET = 1;

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
