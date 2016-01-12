/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.rottentomatoes.movieapi.domain.repository;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rottentomatoes.movieapi.domain.model.Movie;

import io.katharsis.queryParams.RequestParams;
import io.katharsis.repository.ResourceRepository;

@Component
public class MovieRepository implements ResourceRepository<Movie, String> {
    @Autowired
    private SqlSession sqlSession;
    
    @Override
    public <S extends Movie> S save(S entity) {
        return null;
    }

    @Override
    public void delete(String aLong) {
    }

	@Override
	public Movie findOne(String movieId, RequestParams requestParams) {

        Map<String, Object> filters = requestParams.getFilters();
        Map<String, Object> selectParams = new HashMap<>();
        
        selectParams.put("id", movieId);
        
        // Do not just blindly pass filters into myBatis. Explicitly cast and assign for security's sake.
        if(filters != null){
            // NPE will not be thrown if filters.get("xx") is null. MyBatis mapper expects nulls (wide open no limit).
	        selectParams.put("actorLimit", (Integer)filters.get("actorLimit"));
	        selectParams.put("reviewLimit", (Integer)filters.get("reviewLimit"));
            selectParams.put("videoLimit", (Integer)filters.get("videoLimit"));
        }
        
        Movie movie = sqlSession.selectOne("com.rottentomatoes.movieapi.mappers.MovieMapper.selectMovieById", selectParams);
        return movie;
	}

	@Override
	public Iterable<Movie> findAll(RequestParams requestParams) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<Movie> findAll(Iterable<String> ids, RequestParams requestParams) {
		// TODO Auto-generated method stub
		return null;
	}
}
