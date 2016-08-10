package com.rottentomatoes.movieapi.domain.repository;

import com.rottentomatoes.movieapi.domain.model.Critic;
import io.katharsis.queryParams.RequestParams;
import io.katharsis.repository.ResourceRepository;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
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
        // Return list of all critics. Allow filter by last name


        Map<String, Object> selectParams = new HashMap<>();
        selectParams.put("limit", getLimit("", requestParams));
        selectParams.put("offset", getOffset("", requestParams));

        if(requestParams.getFilters() != null && requestParams.getFilters().containsKey("initial")){
            selectParams.put("initial",requestParams.getFilters().get("initial")+"%");
        }

        List<Critic> critics = sqlSession.selectList("com.rottentomatoes.movieapi.mappers.CriticMapper.selectAllCritics", selectParams);
        return critics;

    }

    @Override
    public Iterable<Critic> findAll(Iterable<String> ids, RequestParams requestParams) {
        // TODO Auto-generated method stub
        return null;
    }

}
