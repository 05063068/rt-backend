package com.rottentomatoes.movieapi.domain.repository;

import com.rottentomatoes.movieapi.domain.meta.RelatedMetaDataInformation;
import com.rottentomatoes.movieapi.domain.model.AbstractModel;
import com.rottentomatoes.movieapi.domain.model.Movie;
import com.rottentomatoes.movieapi.domain.model.VideoClip;
import io.katharsis.queryParams.RequestParams;
import io.katharsis.repository.MetaRepository;
import io.katharsis.repository.RelationshipRepository;
import io.katharsis.response.MetaDataEnabledList;
import io.katharsis.response.MetaInformation;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Component
public class MovieToVideoClipRepository extends AbstractRepository implements RelationshipRepository<Movie, String, VideoClip, String>, MetaRepository {
    
    

    @Override
    public void setRelation(Movie movie, String s, String s2) {

    }

    @Override
    public void setRelations(Movie movie, Iterable<String> iterable, String s) {

    }

    @Override
    public void addRelations(Movie movie, Iterable<String> iterable, String s) {

    }

    @Override
    public void removeRelations(Movie movie, Iterable<String> iterable, String s) {

    }

    @Override
    public VideoClip findOneTarget(String s, String s2, RequestParams requestParams) {
        return null;
    }

    @Override
    public MetaDataEnabledList<VideoClip> findManyTargets(String movieId, String fieldName, RequestParams requestParams) {
        Map<String, Object> selectParams = new HashMap<>();
        selectParams.put("movie_id", movieId);
        selectParams.put("limit", getLimit(fieldName, requestParams));
        selectParams.put("offset", getOffset(fieldName, requestParams));

        MetaDataEnabledList<VideoClip> videoClipList;
        videoClipList = new MetaDataEnabledList<>(sqlSession.selectList("com.rottentomatoes.movieapi.mappers.VideoClipMapper.selectVideoClipsForMovie", selectParams));

        this.getClass();
        return videoClipList;
    }

    @Override
    public MetaInformation getMetaInformation(Object root, Iterable resources, RequestParams requestParams, Serializable castedResourceId) {
        Map<String, Object> selectParams = new HashMap<>();
        selectParams.put("movie_id", castedResourceId);

        RelatedMetaDataInformation metaData = sqlSession.selectOne("com.rottentomatoes.movieapi.mappers.VideoClipMapper.selectVideoClipCountForMovie", selectParams);
        if(root instanceof RelationshipRepository) {
            metaData.setRequestParams(requestParams);
        }

        return metaData;
    }
}