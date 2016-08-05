package com.rottentomatoes.movieapi.domain.repository;

import com.rottentomatoes.movieapi.domain.model.Critic;
import com.rottentomatoes.movieapi.domain.model.Review;
import io.katharsis.queryParams.RequestParams;
import io.katharsis.repository.RelationshipRepository;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("rawtypes")
@Component
public class CriticToReviewRepository extends AbstractRepository implements RelationshipRepository<Critic, String, Review, String> {

    @Override
    public void setRelation(Critic critic, String s, String s2) {

    }

    @Override
    public void setRelations(Critic critic, Iterable<String> iterable, String s) {

    }

    @Override
    public void addRelations(Critic critic, Iterable<String> iterable, String s) {

    }

    @Override
    public void removeRelations(Critic critic, Iterable<String> iterable, String s) {

    }

    @Override
    public Review findOneTarget(String criticId, String fieldName, RequestParams requestParams) {
        return null;
    }

    @Override
    public Iterable<Review> findManyTargets(String criticId, String fieldName, RequestParams requestParams) {
        Map<String, Object> selectParams = new HashMap<>();
        selectParams.put("critic_id", criticId);
        selectParams.put("limit", getLimit(fieldName, requestParams));
        selectParams.put("offset", getOffset(fieldName, requestParams));

        return sqlSession.selectList("com.rottentomatoes.movieapi.mappers.ReviewMapper.selectReviewsByCritic", selectParams);
    }

}
