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
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rottentomatoes.movieapi.domain.model.Movie;
import com.rottentomatoes.movieapi.domain.model.MovieList;

import io.katharsis.queryParams.RequestParams;
import io.katharsis.repository.ResourceRepository;
import io.katharsis.resource.exception.ResourceNotFoundException;
@Component
public class MovieListRepository implements ResourceRepository<MovieList, String> {
    @Autowired
    private SqlSession sqlSession;
    
    @Override
    public <S extends MovieList> S save(S entity) {
        return null;
    }

    @Override
    public void delete(String aLong) {
    }

	@Override
	public MovieList findOne(String listId, RequestParams requestParams) {
		MovieList list = new MovieList();
		
		switch(listId){
			case "box-office":
			case "in-theaters":
			case "upcoming":
			
			Map<String, Object> selectParams = new HashMap<>();        
	        List<Movie> movies = sqlSession.selectList("com.rottentomatoes.movieapi.mappers.MovieListMapper.selectBoxOfficeMovies", selectParams);
	        
	        list.setId(listId); // TODO - return same data for any list for now
			list.setMovies(movies);        
			return list;
			
			default:
				throw new ResourceNotFoundException("Invalid list type");
		}
	}

	@Override
	
	public Iterable<MovieList> findAll(RequestParams requestParams) {
		return null;
	}

	@Override
	public Iterable<MovieList> findAll(Iterable<String> ids, RequestParams requestParams) {
		// TODO Auto-generated method stub
		return null;
	}
}
