package com.rottentomatoes.movieapi.domain.repository;

import java.io.Serializable;

import com.rottentomatoes.movieapi.domain.repository.ems.EmsRouter;
import com.rottentomatoes.movieapi.enums.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.katharsis.queryParams.PaginationKeys;
import io.katharsis.queryParams.RequestParams;

@Component
public class AbstractRepository implements Serializable {

    @Autowired
    protected EmsRouter emsRouter;

    String LIMIT = "Limit";
    Integer DEFAULT_LIMIT = 10;
    Integer DEFAULT_OFFSET = 0;

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

    protected static Country getCountry(RequestParams requestParams) {
        if (requestParams.getFilters() != null && requestParams.getFilters().get("country") != null) {
            return Country.getCountryEnumFromString((String) requestParams.getFilters().get("country"));
        } else {
            return Country.getDefault();
        }
    }
}
