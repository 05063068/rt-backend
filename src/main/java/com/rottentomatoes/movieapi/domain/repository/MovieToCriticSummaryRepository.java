package com.rottentomatoes.movieapi.domain.repository;

import com.rottentomatoes.movieapi.domain.model.CriticSummary;
import com.rottentomatoes.movieapi.domain.model.Movie;
import io.katharsis.queryParams.RequestParams;
import io.katharsis.repository.RelationshipRepository;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class MovieToCriticSummaryRepository implements RelationshipRepository<Movie, String , CriticSummary, String> {
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
    public CriticSummary findOneTarget(String movieId, String fieldName, RequestParams requestParams) {
        Map<String, Object> selectParams = new HashMap<>();
        selectParams.put("movie_id", movieId);
        selectParams.put("country", "us");
        CriticSummary criticSummary = sqlSession.selectOne("com.rottentomatoes.movieapi.mappers.CriticSummaryMapper.selectCriticSummaryForMovie", selectParams);
        return criticSummary;
    }

    @Override
    public Iterable<CriticSummary> findManyTargets(String s, String s2, RequestParams requestParams) {
        return null;
    }
}
