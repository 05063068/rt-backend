package com.rottentomatoes.movieapi.domain.repository;

import java.util.HashMap;
import java.util.Map;

import com.rottentomatoes.movieapi.domain.model.AmazonInfo;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.katharsis.queryParams.RequestParams;
import io.katharsis.repository.ResourceRepository;

@Component
public class AmazonInfoRepository extends AbstractRepository implements ResourceRepository<AmazonInfo, String> {
    @Override
    public <S extends AmazonInfo> S save(S entity) {
        return null;
    }

    @Override
    public void delete(String id) {

    }

    @Override
    public AmazonInfo findOne(String id, RequestParams requestParams) {

        Map<String, Object> selectParams = new HashMap<>();
        selectParams.put("id", id);

        AmazonInfo genre = sqlSession.selectOne("com.rottentomatoes.movieapi.mappers.AmazonInfoMapper.selectAmazonInfoById", selectParams);
        return genre;
    }

    @Override
    public Iterable<AmazonInfo> findAll(RequestParams requestParams) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Iterable<AmazonInfo> findAll(Iterable<String> ids, RequestParams requestParams) {
        // TODO Auto-generated method stub
        return null;
    }

}
