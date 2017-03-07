
package com.rottentomatoes.movieapi.domain.repository;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class AbstractRepository implements Serializable {

    @Autowired
    protected EmsRouter emsRouter;

    @Autowired
    protected Environment environment;
}
