package com.rottentomatoes.movieapi.domain.repository;

import java.util.HashMap;
import java.util.Map;

import com.rottentomatoes.movieapi.domain.model.ItunesInfo;
import org.springframework.stereotype.Component;

import io.katharsis.queryParams.RequestParams;
import io.katharsis.repository.ResourceRepository;

@Component
public class ItunesInfoRepository extends AbstractRepository implements ResourceRepository<ItunesInfo, String> {
    
    @Override
    public <S extends ItunesInfo> S save(S entity) {
        return null;
    }

    @Override
    public void delete(String id) {

    }

    @Override
    public ItunesInfo findOne(String id, RequestParams requestParams) {

        Map<String, Object> selectParams = new HashMap<>();
        selectParams.put("id", id);

        ItunesInfo itunesInfo = sqlSession.selectOne("com.rottentomatoes.movieapi.mappers.ItunesInfoMapper.selectItunesInfoById", selectParams);
        return itunesInfo;
    }

    @Override
    public Iterable<ItunesInfo> findAll(RequestParams requestParams) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Iterable<ItunesInfo> findAll(Iterable<String> ids, RequestParams requestParams) {
        // TODO Auto-generated method stub
        return null;
    }

}
