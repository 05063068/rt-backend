package com.rottentomatoes.movieapi.domain.repository;

import com.rottentomatoes.movieapi.domain.model.AudienceSummary;
import com.rottentomatoes.movieapi.domain.model.Movie;
import io.katharsis.queryParams.RequestParams;
import io.katharsis.repository.RelationshipRepository;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class MovieToAudienceSummaryRepository implements RelationshipRepository<Movie, String, AudienceSummary, String> {
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
    public AudienceSummary findOneTarget(String movieId, String fieldName, RequestParams requestParams) {
        Map<String, Object> selectParams = new HashMap<>();
        selectParams.put("movie_id", movieId);

        AudienceSummary audienceSummary = sqlSession.selectOne("com.rottentomatoes.movieapi.mappers.AudienceSummaryMapper.selectAudienceSummaryForMovie", selectParams);
        return audienceSummary;
    }

    @Override
    public Iterable<AudienceSummary> findManyTargets(String s, String s2, RequestParams requestParams) {
        return null;
    }
}
