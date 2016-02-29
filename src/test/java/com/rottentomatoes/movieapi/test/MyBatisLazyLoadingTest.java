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
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.rottentomatoes.movieapi.domain.model.Movie;
import com.rottentomatoes.movieapi.domain.model.MovieCast;
import com.rottentomatoes.movieapi.test.logback.LogbackCapturingAppender;

import lombok.extern.slf4j.Slf4j;

import org.slf4j.LoggerFactory;

@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource(locations={ "classpath:application.properties" })
@Slf4j
public class MyBatisLazyLoadingTest {
	@Autowired
	private SqlSession sqlSession;
	
    @Before
    public void setUp(){
	
    }
    
    @After
    public void tearDown(){
    	LogbackCapturingAppender.Factory.cleanUp();	
    }
        
    @Test
    public void test() {
    	
    	// Given
        LogbackCapturingAppender capturing = LogbackCapturingAppender.Factory.weaveInto(LoggerFactory.getLogger("org.mybatis"));
        
        Movie movie = sqlSession.selectOne("com.rottentomatoes.movieapi.mappers.MovieMapper.selectMovieById", 9);
                      
        // then
        log.info("Captured message:" + capturing.getCapturedLogMessage());
        
    }

}
