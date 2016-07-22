package com.rottentomatoes.movieapi.domain.repository;

import com.rottentomatoes.movieapi.domain.model.Critic;
import io.katharsis.queryParams.RequestParams;
import io.katharsis.repository.ResourceRepository;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CriticRepository extends AbstractRepository implements ResourceRepository<Critic, String> {

    @Override
    public <S extends Critic> S save(S entity) {
        return null;
    }

    @Override
    public void delete(String id) {

    }

    @Override
    public Critic findOne(String id, RequestParams requestParams) {

        Map<String, Object> selectParams = new HashMap<>();
        selectParams.put("id", id);

        Critic critic = sqlSession.selectOne("com.rottentomatoes.movieapi.mappers.CriticMapper.selectCriticById", selectParams);
        return critic;
    }

    @Override
    public Iterable<Critic> findAll(RequestParams requestParams) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Iterable<Critic> findAll(Iterable<String> ids, RequestParams requestParams) {
        // TODO Auto-generated method stub
        return null;
    }

}
