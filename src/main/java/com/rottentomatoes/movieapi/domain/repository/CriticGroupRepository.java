package com.rottentomatoes.movieapi.domain.repository;

import com.rottentomatoes.movieapi.domain.model.CriticGroup;
import io.katharsis.queryParams.RequestParams;
import io.katharsis.repository.ResourceRepository;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CriticGroupRepository extends AbstractRepository implements ResourceRepository<CriticGroup, String> {

    @Override
    public <S extends CriticGroup> S save(S entity) {
        return null;
    }

    @Override
    public void delete(String id) {

    }

    @Override
    public CriticGroup findOne(String id, RequestParams requestParams) {

        Map<String, Object> selectParams = new HashMap<>();
        selectParams.put("id", id);
        CriticGroup criticGroup = sqlSession.selectOne("com.rottentomatoes.movieapi.mappers.CriticGroupMapper.selectCriticGroupById", selectParams);
        return criticGroup;
    }

    @Override
    public Iterable<CriticGroup> findAll(RequestParams requestParams) {

        Map<String, Object> selectParams = new HashMap<>();
        selectParams.put("limit", getLimit("", requestParams));
        selectParams.put("offset", getOffset("", requestParams));
        List<CriticGroup> criticGroups = sqlSession.selectList("com.rottentomatoes.movieapi.mappers.CriticGroupMapper.selectAllCriticGroups", selectParams);
        return criticGroups;

    }

    @Override
    public Iterable<CriticGroup> findAll(Iterable<String> ids, RequestParams requestParams) {
        // TODO Auto-generated method stub
        return null;
    }

}
