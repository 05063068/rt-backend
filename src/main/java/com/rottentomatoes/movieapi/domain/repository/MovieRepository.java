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

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.flixster.image.Environment;
import com.flixster.image.IdGenerator;
import com.flixster.image.ImageFormat;
import com.flixster.image.ImageType;
import com.rottentomatoes.movieapi.domain.meta.RootMetaDataInformation;
import io.katharsis.repository.MetaRepository;
import io.katharsis.response.MetaInformation;
import org.springframework.stereotype.Component;

import com.rottentomatoes.movieapi.domain.model.Movie;

import io.katharsis.queryParams.RequestParams;
import io.katharsis.repository.ResourceRepository;

@SuppressWarnings("rawtypes")
@Component
public class MovieRepository extends AbstractRepository implements ResourceRepository<Movie, String>, MetaRepository {

    @Override
    public <S extends Movie> S save(S entity) {
        return null;
    }

    @Override
    public void delete(String aLong) {
    }

    @Override
    public Movie findOne(String movieId, RequestParams requestParams) {
        Map<String, Object> selectParams = new HashMap<>();
        selectParams.put("id", movieId);
        Movie movie = sqlSession.selectOne("com.rottentomatoes.movieapi.mappers.MovieMapper.selectMovieById", selectParams);

        if (movie.getPosterImage() != null) {
            Long id = Long.parseLong(movie.getPosterImage().getId());
            ImageType type = ImageType.MOVIE;
            Calendar c = Calendar.getInstance();
            c.add(Calendar.DATE, 45);
            Date expiry = c.getTime();
            Environment environment = Environment.PROD;
            ImageFormat format = getImageFormat(movie.getPosterImage().getFormat());
            int width = movie.getPosterImage().getOriginalWidth();
            int height = movie.getPosterImage().getOriginalHeight();

           movie.getPosterImage().setThumborId(getThumborId(id, type, expiry, environment, format, width, height));
        }

        if (movie.getHeroImage() != null) {
            Long id = Long.parseLong(movie.getHeroImage().getId());
            ImageType type = ImageType.MOVIE;
            Calendar c = Calendar.getInstance();
            c.add(Calendar.DATE, 45);
            Date expiry = c.getTime();
            Environment environment = Environment.PROD;
            ImageFormat format = getImageFormat(movie.getHeroImage().getFormat());
            int width = movie.getHeroImage().getOriginalWidth();
            int height = movie.getHeroImage().getOriginalHeight();

            movie.getHeroImage().setThumborId(getThumborId(id, type, expiry, environment, format, width, height));
        }

        return movie;
    }

    @Override
    public Iterable<Movie> findAll(RequestParams requestParams) {
        return null;
    }

    @Override
    public Iterable<Movie> findAll(Iterable<String> ids, RequestParams requestParams) {
        return null;
    }

    @Override
    public MetaInformation getMetaInformation(Object root, Iterable resources, RequestParams requestParams, Serializable castedResourceId) {
        RootMetaDataInformation metaData = new RootMetaDataInformation();
        metaData.setRequestParams(requestParams);
        return metaData;
    }
}
