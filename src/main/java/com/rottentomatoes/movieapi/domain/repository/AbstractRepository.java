package com.rottentomatoes.movieapi.domain.repository;

import java.io.Serializable;

import com.rottentomatoes.movieapi.enums.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.katharsis.queryParams.PaginationKeys;
import io.katharsis.queryParams.RequestParams;

@Component
public class AbstractRepository implements Serializable {

    @Autowired
    protected EmsRouter emsRouter;
}
