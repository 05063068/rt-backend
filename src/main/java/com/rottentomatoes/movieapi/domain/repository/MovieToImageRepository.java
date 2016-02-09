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

import com.rottentomatoes.movieapi.domain.meta.RelatedMetaDataInformation;
import com.rottentomatoes.movieapi.domain.model.AbstractModel;
import io.katharsis.repository.MetaRepository;
import io.katharsis.response.MetaDataEnabledList;
import io.katharsis.response.MetaInformation;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rottentomatoes.movieapi.domain.model.Image;
import com.rottentomatoes.movieapi.domain.model.Movie;

import io.katharsis.queryParams.RequestParams;
import io.katharsis.repository.RelationshipRepository;

@Component
public class MovieToImageRepository implements RelationshipRepository<Movie, String, Image, String>, MetaRepository {
    @Autowired
    private SqlSession sqlSession;
    
    @Override
    public void addRelations(Movie arg0, Iterable<String> arg1, String arg2) {
    }

    @Override
    public void removeRelations(Movie arg0, Iterable<String> arg1, String arg2) {
    }

    @Override
    public void setRelation(Movie arg0, String arg1, String arg2) {
    }
    
    @Override
    public void setRelations(Movie arg0, Iterable<String> arg1, String arg2) {
    }

	@Override
	public Image findOneTarget(String id, String fieldName, RequestParams requestParams) {
		return null;
	}

	@Override
	public MetaDataEnabledList<Image> findManyTargets(String movieId, String fieldName, RequestParams requestParams) {
        Map<String, Object> selectParams = new HashMap<>();
        selectParams.put("movie_id", movieId);
        selectParams.put("limit", getLimit(fieldName, requestParams));

        MetaDataEnabledList<Image> imageList = new MetaDataEnabledList(sqlSession.selectList("com.rottentomatoes.movieapi.mappers.ImageMapper.selectImagesForMovie", selectParams));
        return imageList;
	}

    @Override
    public MetaInformation getMetaInformation(Object root, Iterable resources, RequestParams requestParams) {
        String movieId = ((AbstractModel)root).getId();
        Map<String, Object> selectParams = new HashMap<>();
        selectParams.put("movie_id", movieId);

        RelatedMetaDataInformation imageMetaData = sqlSession.selectOne("com.rottentomatoes.movieapi.mappers.ImageMapper.selectImageCountForMovie", selectParams);
        return imageMetaData;
    }
}
