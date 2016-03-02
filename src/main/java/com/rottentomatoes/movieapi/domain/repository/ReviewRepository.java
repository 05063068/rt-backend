package com.rottentomatoes.movieapi.domain.repository;

import org.springframework.stereotype.Component;

import com.rottentomatoes.movieapi.domain.model.Review;

import io.katharsis.queryParams.RequestParams;
import io.katharsis.repository.ResourceRepository;

@Component
public class ReviewRepository extends AbstractRepository implements ResourceRepository<Review, String> {
    
    @Override
    public <S extends Review> S save(S entity) {
        return null;
    }

    @Override
    public void delete(String aString) {

    }

	@Override
	public Review findOne(String reviewId, RequestParams requestParams) {
        Review review = sqlSession.selectOne("com.rottentomatoes.movieapi.mappers.ReviewMapper.selectReviewById", reviewId);
        return review;
	}

	@Override
	public Iterable<Review> findAll(RequestParams requestParams) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<Review> findAll(Iterable<String> ids, RequestParams requestParams) {
		// TODO Auto-generated method stub
		return null;
	}
}
