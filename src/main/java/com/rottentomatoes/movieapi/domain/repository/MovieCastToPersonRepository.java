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

import com.rottentomatoes.movieapi.domain.model.MovieCast;
import com.rottentomatoes.movieapi.domain.model.Person;

import io.katharsis.queryParams.QueryParams;
import io.katharsis.repository.RelationshipRepository;

@Component
public class MovieCastToPersonRepository implements RelationshipRepository<MovieCast, String, Person, String> {
    @Autowired
    private SqlSession sqlSession;
    
    @Override
    public Iterable<Person> findManyTargets(String movieId, String fieldName, QueryParams requestParams) {
        return null;
    }

    @Override
    public void addRelations(MovieCast arg0, Iterable<String> arg1, String arg2) {
        
    }

    @Override
    public Person findOneTarget(String movieCastId, String fieldName, QueryParams requestParms) {
        Person person = sqlSession.selectOne("com.rottentomatoes.movieapi.mappers.PersonMapper.selectPersonForMovieCast", movieCastId);
        return person;
    }

    @Override
    public void removeRelations(MovieCast arg0, Iterable<String> arg1, String arg2) {
    }

    @Override
    public void setRelation(MovieCast arg0, String arg1, String arg2) {
    }

    @Override
    public void setRelations(MovieCast arg0, Iterable<String> arg1, String arg2) {
    }
}
