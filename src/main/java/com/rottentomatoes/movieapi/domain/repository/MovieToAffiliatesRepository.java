package com.rottentomatoes.movieapi.domain.repository;

import com.rottentomatoes.movieapi.domain.model.Affiliates;
import com.rottentomatoes.movieapi.domain.model.AmazonInfo;
import com.rottentomatoes.movieapi.domain.model.ItunesInfo;
import com.rottentomatoes.movieapi.domain.model.Movie;
import com.rottentomatoes.movieapi.domain.model.SonicInfo;
import com.rottentomatoes.movieapi.domain.model.VuduInfo;
import io.katharsis.queryParams.RequestParams;
import io.katharsis.repository.RelationshipRepository;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class MovieToAffiliatesRepository extends AbstractRepository implements RelationshipRepository<Movie, String, Affiliates, String> {

    
    @Override
    public void setRelation(Movie source, String targetId, String fieldName) {

    }

    @Override
    public void setRelations(Movie source, Iterable<String> targetIds, String fieldName) {

    }

    @Override
    public void addRelations(Movie source, Iterable<String> targetIds, String fieldName) {

    }

    @Override
    public void removeRelations(Movie source, Iterable<String> targetIds, String fieldName) {

    }

    @Override
    public Affiliates findOneTarget(String movieId, String fieldName, RequestParams requestParams) {
        Affiliates affiliate = new Affiliates();
        affiliate.setId(movieId);

        Map<String, Object> selectParams = new HashMap<>();
        selectParams.put("movie_id", movieId);

        List<AmazonInfo> amazonInfo = sqlSession.selectList("com.rottentomatoes.movieapi.mappers.AmazonInfoMapper.selectAmazonInfoForMovie", selectParams);
        List<ItunesInfo> itunesInfo = sqlSession.selectList("com.rottentomatoes.movieapi.mappers.ItunesInfoMapper.selectItunesInfoForMovie", selectParams);
        List<SonicInfo> sonicInfo = sqlSession.selectList("com.rottentomatoes.movieapi.mappers.SonicInfoMapper.selectSonicInfoForMovie", selectParams);
        List<VuduInfo> vuduInfo = sqlSession.selectList("com.rottentomatoes.movieapi.mappers.VuduInfoMapper.selectVuduInfoForMovie", selectParams);

        affiliate.setAmazonInfo(amazonInfo);
        affiliate.setItunesInfo(itunesInfo);
        affiliate.setSonicInfo(sonicInfo);
        affiliate.setVuduInfo(vuduInfo);

        return affiliate;
    }

    @Override
    public Iterable<Affiliates> findManyTargets(String sourceId, String fieldName, RequestParams requestParams) {
        return null;
    }
}
