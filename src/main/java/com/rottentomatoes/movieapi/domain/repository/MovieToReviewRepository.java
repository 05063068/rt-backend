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

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rottentomatoes.movieapi.domain.model.MovieCast;
import com.rottentomatoes.movieapi.domain.model.Review;
import com.rottentomatoes.movieapi.domain.model.Movie;

import io.katharsis.queryParams.RequestParams;
import io.katharsis.repository.RelationshipRepository;

@Component
public class MovieToReviewRepository implements RelationshipRepository<Movie, Long, Review, Long> {
    @Autowired
    private SqlSession sqlSession;
    
    @Override
    public Iterable<Review> findManyTargets(Long movieId, String fieldName, RequestParams requestParams) {
        List<Review> reviewList = sqlSession.selectList("com.rottentomatoes.movieapi.mappers.ReviewMapper.selectReviewsForMovie", movieId);
        return reviewList;
    }

    @Override
    public void addRelations(Movie arg0, Iterable<Long> arg1, String arg2) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public Review findOneTarget(Long movieId, String fieldName, RequestParams requestParms) {
        return null;
    }

    @Override
    public void removeRelations(Movie arg0, Iterable<Long> arg1, String arg2) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setRelation(Movie arg0, Long arg1, String arg2) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setRelations(Movie arg0, Iterable<Long> arg1, String arg2) {
        // TODO Auto-generated method stub
        
    }
}
