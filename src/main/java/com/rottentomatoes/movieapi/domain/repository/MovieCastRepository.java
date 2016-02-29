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

import io.katharsis.queryParams.RequestParams;
import io.katharsis.repository.ResourceRepository;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rottentomatoes.movieapi.domain.model.MovieCast;

@Component
public class MovieCastRepository extends AbstractRepository implements ResourceRepository<MovieCast, String> {
    
    
    
    @Override
    public void delete(String aString) {

    }

    @Override
    public <S extends MovieCast> S save(S arg0) {
        return null;
    }

	@Override
	public MovieCast findOne(String movieCastId, RequestParams requestParams) {
		Map<String, Object> selectParams = new HashMap<>();
		selectParams.put("movieCastId", movieCastId);
        MovieCast movieCast = sqlSession.selectOne("com.rottentomatoes.movieapi.mappers.MovieCastMapper.selectMovieCastById", selectParams);
        return movieCast;
	}

	@Override
	public Iterable<MovieCast> findAll(RequestParams requestParams) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<MovieCast> findAll(Iterable<String> ids, RequestParams requestParams) {
		// TODO Auto-generated method stub
		return null;
	}

}
