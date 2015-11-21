/**
 * Make sure model objects are tested for equality by ID 
 * */
package com.rottentomatoes.movieapi.test;

import static org.junit.Assert.*;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggingEvent;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.rottentomatoes.movieapi.domain.model.Movie;
import com.rottentomatoes.movieapi.domain.model.MovieCast;
import com.rottentomatoes.movieapi.test.log4j.TestAppender;

import static org.hamcrest.CoreMatchers.is;

@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource(locations={ "classpath:application.properties" })
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class MyBatisLazyLoadingTest {
    @Autowired
    private SqlSession sqlSession;
    
    @Test
    public void test() {
        final TestAppender appender = new TestAppender();
        final Logger logger = Logger.getRootLogger();
        logger.addAppender(appender);
        try {
            Movie movie = sqlSession.selectOne("com.rottentomatoes.movieapi.mappers.MovieMapper.selectMovieById", 9);
            
            Movie movie1 = new Movie();
            logger.info(movie1.getClass().getName());
            
            logger.info(movie.getClass().getName());
            logger.info("superclass:"+movie.getClass().getSuperclass().getName());
            
            Iterable<MovieCast> cast = movie.getMovieCast();
            
            logger.info(movie.getClass().getName());
            
            
        }
        finally {
            logger.removeAppender(appender);
        }

        final List<LoggingEvent> log = appender.getLog();
        
        for(LoggingEvent e : log){
            System.out.println(e.getMessage());        
        }
        
    }

}
