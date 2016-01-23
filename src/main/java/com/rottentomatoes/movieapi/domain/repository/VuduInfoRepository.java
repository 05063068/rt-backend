package com.rottentomatoes.movieapi.domain.repository;

import java.util.HashMap;
import java.util.Map;

import com.rottentomatoes.movieapi.domain.model.VuduInfo;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.katharsis.queryParams.RequestParams;
import io.katharsis.repository.ResourceRepository;

@Component
public class VuduInfoRepository implements ResourceRepository<VuduInfo, String> {
    @Autowired
    private SqlSession sqlSession;

    @Override
    public <S extends VuduInfo> S save(S entity) {
        return null;
    }

    @Override
    public void delete(String id) {

    }

    @Override
    public VuduInfo findOne(String id, RequestParams requestParams) {

        Map<String, Object> selectParams = new HashMap<>();
        selectParams.put("id", id);

        VuduInfo vuduInfo = sqlSession.selectOne("com.rottentomatoes.movieapi.mappers.VuduInfoMapper.selectVuduInfoById", selectParams);
        return vuduInfo;
    }

    @Override
    public Iterable<VuduInfo> findAll(RequestParams requestParams) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Iterable<VuduInfo> findAll(Iterable<String> ids, RequestParams requestParams) {
        // TODO Auto-generated method stub
        return null;
    }

}
