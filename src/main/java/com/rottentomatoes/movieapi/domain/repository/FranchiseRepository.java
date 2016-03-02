package com.rottentomatoes.movieapi.domain.repository;

import java.util.HashMap;
import java.util.Map;

import com.rottentomatoes.movieapi.domain.model.Franchise;
import org.springframework.stereotype.Component;

import io.katharsis.queryParams.RequestParams;
import io.katharsis.repository.ResourceRepository;

@Component
public class FranchiseRepository  extends AbstractRepository implements ResourceRepository<Franchise, String> {
    
    @Override
    public <S extends Franchise> S save(S entity) {
        return null;
    }

    @Override
    public void delete(String id) {

    }

    @Override
    public Franchise findOne(String id, RequestParams requestParams) {

        Map<String, Object> selectParams = new HashMap<>();
        selectParams.put("id", id);

        Franchise franchise = sqlSession.selectOne("com.rottentomatoes.movieapi.mappers.FranchiseMapper.selectFranchiseById", selectParams);
        return franchise;
    }

    @Override
    public Iterable<Franchise> findAll(RequestParams requestParams) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Iterable<Franchise> findAll(Iterable<String> ids, RequestParams requestParams) {
        // TODO Auto-generated method stub
        return null;
    }

}
