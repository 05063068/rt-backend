package com.rottentomatoes.movieapi.domain.repository;

import com.rottentomatoes.movieapi.domain.model.VideoClip;
import io.katharsis.queryParams.RequestParams;
import io.katharsis.repository.ResourceRepository;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VideoClipRepository implements ResourceRepository<VideoClip, String> {

    @Autowired
    private SqlSession sqlSession;

    @Override
    public void delete(String s) {}

    @Override
    public <S extends VideoClip> S save(S arg0) {
        return null;
    }

    @Override
    public VideoClip findOne(String videoClipId, RequestParams requestParams) {
        VideoClip videoClip = sqlSession.selectOne("com.rottentomatoes.movieapi.mappers.VideoClipMapper.selectVideoClipById", videoClipId);
        return videoClip;
    }

    @Override
    public Iterable<VideoClip> findAll(RequestParams requestParams) {
        return null;
    }

    @Override
    public Iterable<VideoClip> findAll(Iterable<String> iterable, RequestParams requestParams) {
        return null;
    }
}
