/**
 * Make sure model objects are tested for equality by ID 
 * */
package com.orttentomatoes.movieapi.test;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.rottentomatoes.movieapi.domain.model.Movie;

import io.katharsis.queryParams.RequestParams;
import io.katharsis.request.path.JsonPath;
import io.katharsis.request.path.PathBuilder;
import io.katharsis.request.path.PathIds;
import io.katharsis.request.path.ResourcePath;
import io.katharsis.response.LinksInformation;
import io.katharsis.response.MetaInformation;
import io.katharsis.response.ResourceResponse;
import scala.annotation.meta.setter;

public class ModelEqualityTest {
    @Test
    public void test() {
        Movie m1 = new Movie();
        m1.setId(1);
        
        Movie m2 = new Movie();
        m2.setId(1);
        
        Movie m3 = new Movie();
        m2.setId(2);
        
        assertEquals(m1,m2);
        assertNotEquals(m1,m3);
        
        assertEquals(m1.hashCode(), m2.hashCode());
        assertNotEquals(m1.hashCode(),m3.hashCode());        
        
    }

}
