package com.orttentomatoes.movieapi.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.web.WebAppConfiguration;

import com.rottentomatoes.movieapi.MovieApiApplication;

import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MovieApiApplication.class)
@WebAppConfiguration
public class MovieApiApplicationTests {

	@Test
	public void contextLoads() {
	}

}
