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

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rottentomatoes.movieapi.domain.model.Movie;
import com.rottentomatoes.movieapi.domain.model.Review;

import io.katharsis.queryParams.RequestParams;
import io.katharsis.repository.ResourceRepository;

@Component
public class ReviewRepository implements ResourceRepository<Review, Long> {
    @Autowired
    private SqlSession sqlSession;
    
    @Override
    public <S extends Review> S save(S entity) {
        return null;
    }

    @Override
    public Review findOne(Long reviewId, RequestParams requestParams) {
        Review review = sqlSession.selectOne("com.rottentomatoes.movieapi.mappers.ReviewMapper.selectReviewById", reviewId);
        return review;
        
    }

    @Override
    public Iterable<Review> findAll(RequestParams requestParams) {
        return findAll(null, requestParams);
    }

    @Override
    public Iterable<Review> findAll(Iterable<Long> taskIds, RequestParams requestParams) {
        return null;
    }

    @Override
    public void delete(Long aLong) {

    }
}
