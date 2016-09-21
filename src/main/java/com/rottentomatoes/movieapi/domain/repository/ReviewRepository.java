package com.rottentomatoes.movieapi.domain.repository;

import com.rottentomatoes.movieapi.domain.model.Critic;
import org.springframework.stereotype.Component;

import com.rottentomatoes.movieapi.domain.model.Review;

import io.katharsis.queryParams.RequestParams;
import io.katharsis.repository.ResourceRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        // Return list of all reviews, latest first
        Map<String, Object> selectParams = new HashMap<>();
        selectParams.put("limit", getLimit("", requestParams));
        selectParams.put("offset", getOffset("", requestParams));

        // Accepted category filter values are: 'theatrical', 'dvd' or 'quick'
        if(requestParams.getFilters() != null && requestParams.getFilters().containsKey("category")) {
            selectParams.put("category", requestParams.getFilters().get("category"));
        }

        List<Review> reviews = sqlSession.selectList("com.rottentomatoes.movieapi.mappers.ReviewMapper.selectAllReviews", selectParams);
        return reviews;
    }

    @Override
    public Iterable<Review> findAll(Iterable<String> ids, RequestParams requestParams) {
        // TODO Auto-generated method stub
        return null;
    }
}
