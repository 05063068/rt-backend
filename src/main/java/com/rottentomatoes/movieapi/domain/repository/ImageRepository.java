package com.rottentomatoes.movieapi.domain.repository;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rottentomatoes.movieapi.domain.model.Image;

import io.katharsis.queryParams.RequestParams;
import io.katharsis.repository.ResourceRepository;

@Component
public class ImageRepository implements ResourceRepository<Image, String> {
    @Autowired
    private SqlSession sqlSession;
    
    @Override
    public <S extends Image> S save(S entity) {
        return null;
    }
        
    @Override
    public void delete(String id) {

    }

	@Override
	public Image findOne(String imageId, RequestParams requestParams) {
        Image image = sqlSession.selectOne("com.rottentomatoes.movieapi.mappers.ImageMapper.selectImageById", imageId);
        return image;
	}

	@Override
	public Iterable<Image> findAll(RequestParams requestParams) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<Image> findAll(Iterable<String> ids, RequestParams requestParams) {
		// TODO Auto-generated method stub
		return null;
	}
}
