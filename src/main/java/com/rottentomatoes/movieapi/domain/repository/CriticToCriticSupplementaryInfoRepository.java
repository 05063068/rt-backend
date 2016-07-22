package com.rottentomatoes.movieapi.domain.repository;

import com.rottentomatoes.movieapi.domain.model.Critic;
import com.rottentomatoes.movieapi.domain.model.CriticSupplementaryInfo;
import io.katharsis.queryParams.RequestParams;
import io.katharsis.repository.RelationshipRepository;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("rawtypes")
@Component
public class CriticToCriticSupplementaryInfoRepository extends AbstractRepository implements RelationshipRepository<Critic, String, CriticSupplementaryInfo, String> {

    @Override
    public void setRelation(Critic critic, String s, String s2) {

    }

    @Override
    public void setRelations(Critic critic, Iterable<String> iterable, String s) {

    }

    @Override
    public void addRelations(Critic critic, Iterable<String> iterable, String s) {

    }

    @Override
    public void removeRelations(Critic critic, Iterable<String> iterable, String s) {

    }

    @Override
    public CriticSupplementaryInfo findOneTarget(String criticId, String fieldName, RequestParams requestParams) {
        Map<String, Object> selectParams = new HashMap<>();
        selectParams.put("id", criticId);
        CriticSupplementaryInfo criticSupplementaryInfo = sqlSession.selectOne("com.rottentomatoes.movieapi.mappers.CriticMapper.selectCriticSupplementaryInfo", selectParams);

        return criticSupplementaryInfo;
    }

    @Override
    public Iterable<CriticSupplementaryInfo> findManyTargets(String s, String s2, RequestParams requestParams) {
        return null;
    }

}
