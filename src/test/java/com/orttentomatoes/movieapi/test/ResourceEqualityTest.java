/**
 * Make sure resources are tested for equality by url path not reference
 */
package com.orttentomatoes.movieapi.test;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import io.katharsis.queryParams.RequestParams;
import io.katharsis.request.path.JsonPath;
import io.katharsis.request.path.PathBuilder;
import io.katharsis.request.path.PathIds;
import io.katharsis.request.path.ResourcePath;
import io.katharsis.response.LinksInformation;
import io.katharsis.response.MetaInformation;
import io.katharsis.response.ResourceResponse;
import scala.annotation.meta.setter;

public class ResourceEqualityTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void test() {
        JsonPath jsonpath1 = new ResourcePath("resource",new PathIds("1"));
        ResourceResponse response1 = new ResourceResponse("data1", jsonpath1, null, null, null);
        
        JsonPath jsonpath2 = new ResourcePath("resource",new PathIds("1"));
        ResourceResponse response2 = new ResourceResponse("data2", jsonpath2, null, null, null);

        JsonPath jsonpath3 = new ResourcePath("resource",new PathIds("2"));
        ResourceResponse response3 = new ResourceResponse("data3", jsonpath3, null, null, null);

        // Equals
        assertEquals(jsonpath1, jsonpath2);   
        assertEquals(response1, response2);
        assertNotEquals(response1, response3);
        
        // Hashcode
        assertEquals(jsonpath1.hashCode(), jsonpath2.hashCode());   
        assertEquals(response1.hashCode(), response2.hashCode());
        assertNotEquals(response1.hashCode(), response3.hashCode());
        
        // Add to Set
        Set<ResourceResponse> responseSet = new HashSet<>();
        responseSet.add(response1);
        responseSet.add(response2);
        assertEquals(responseSet.size(), 1);
        responseSet.add(response3);
        assertEquals(responseSet.size(), 2);
        
        
        
    }

}
