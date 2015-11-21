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
import java.util.Set;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.rottentomatoes.movieapi.domain.model.Movie;

import io.katharsis.queryParams.QueryParams;
import io.katharsis.queryParams.params.FilterParams;
import io.katharsis.queryParams.params.TypedParams;
import io.katharsis.repository.ResourceRepository;
import lombok.extern.log4j.Log4j;

@Component
@Log4j
public class MovieRepository implements ResourceRepository<Movie, String> {
    @Autowired
    private SqlSession sqlSession;
    
    @Override
    public <S extends Movie> S save(S entity) {
        return null;
    }

    @Override
    public Movie findOne(String movieId, QueryParams requestParams) {
        
        Map<String, Object> selectParams = new HashMap<>();
        selectParams.put("id", movieId);
        selectParams.put("castLimit", 10);
        selectParams.put("reviewLimit", 10);
        
        Map<String, FilterParams> typedFilters = requestParams.getFilters().getParams();
        
        FilterParams movieCastFilter = typedFilters.get("MovieCast");
        if(movieCastFilter != null){
        	Map<String, Set<String>> movieCastFilterParams = movieCastFilter.getParams();
            if(movieCastFilterParams.get("limit") != null){
            	log.info("MovieCast Limit:" + movieCastFilterParams.get("limit"));
                //selectParams.put("castLimit", filters.get("castLimit"));
            }
        }
        
        Movie movie = sqlSession.selectOne("com.rottentomatoes.movieapi.mappers.MovieMapper.selectMovieById", selectParams);
        return movie;
    }

    @Override
    public Iterable<Movie> findAll(QueryParams requestParams) {
        return null;
    }

    @Override
    public void delete(String aLong) {
    }

	@Override
	public Iterable<Movie> findAll(Iterable<String> ids, QueryParams queryParams) {
		return null;
	}
}
