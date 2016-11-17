package com.rottentomatoes.movieapi.domain.repository.critic;

import com.rottentomatoes.movieapi.domain.model.Critic;
import com.rottentomatoes.movieapi.domain.model.CriticSupplementaryInfo;
import com.rottentomatoes.movieapi.domain.repository.AbstractRepository;
import com.rottentomatoes.movieapi.domain.repository.ems.EmsClient;
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
        EmsClient emsClient = emsRouter.fetchEmsClientForEndpoint(this.getClass());
        CriticSupplementaryInfo criticSupplementaryInfo = (CriticSupplementaryInfo)emsClient.callEmsEntity(selectParams, "critic", criticId + "/supplementary-info", CriticSupplementaryInfo.class);

        return criticSupplementaryInfo;
    }

    @Override
    public Iterable<CriticSupplementaryInfo> findManyTargets(String s, String s2, RequestParams requestParams) {
        return null;
    }

}
