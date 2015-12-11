package com.rottentomatoes.movieapi.domain.repository;

import com.rottentomatoes.movieapi.domain.model.Movie;
import com.rottentomatoes.movieapi.domain.model.VideoClip;
import io.katharsis.queryParams.RequestParams;
import io.katharsis.repository.RelationshipRepository;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class MovieToVideoClipRepository implements RelationshipRepository<Movie, String, VideoClip, String> {
    @Autowired
    private SqlSession sqlSession;

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
    public Iterable<VideoClip> findManyTargets(String movieId, String fieldName, RequestParams requestParams) {
        Map<String, Object> selectParams = new HashMap<>();
        selectParams.put("movie_id", movieId);

        List<VideoClip> videoClipList = sqlSession.selectList("com.rottentomatoes.movieapi.mappers.VideoClipMapper.selectVideoClipsForMovie", selectParams);
        return videoClipList;
    }
}