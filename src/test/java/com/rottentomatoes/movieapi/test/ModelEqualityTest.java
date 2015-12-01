/**
 * Make sure model objects are tested for equality by ID 
 * */
package com.rottentomatoes.movieapi.test;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.rottentomatoes.movieapi.domain.model.Movie;

public class ModelEqualityTest {
    @Test
    public void test() {
        Movie m1 = new Movie();
        m1.setId("1");
        
        Movie m2 = new Movie();
        m2.setId("1");
        
        Movie m3 = new Movie();
        m3.setId("2");
        
        assertTrue(m1.equals(m2));
        assertFalse(m1.equals(m3));
        
        assertTrue(m1.hashCode() == m2.hashCode());
        assertFalse(m1.hashCode() == m3.hashCode());        
        
    }

}
