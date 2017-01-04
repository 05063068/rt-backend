package com.rottentomatoes.movieapi.domain.repository.critic;

import com.fasterxml.jackson.databind.type.TypeFactory;
import com.rottentomatoes.movieapi.domain.model.Critic;
import com.rottentomatoes.movieapi.domain.model.Publication;
import com.rottentomatoes.movieapi.domain.repository.AbstractRepository;
import com.rottentomatoes.movieapi.domain.ems.EmsClient;
import io.katharsis.queryParams.RequestParams;
import io.katharsis.repository.RelationshipRepository;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("rawtypes")
@Component
public class CriticToPublicationRepository extends AbstractRepository implements RelationshipRepository<Critic, String, Publication, String> {

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
    public Publication findOneTarget(String criticId, String fieldName, RequestParams requestParams) {
        return null;
    }

    @Override
    public Iterable<Publication> findManyTargets(String criticId, String fieldName, RequestParams requestParams) {
        EmsClient emsClient = emsRouter.fetchEmsClientForEndpoint(this.getClass());
        Map<String, Object> selectParams = new HashMap<>();
        return (Iterable<Publication>) emsClient.callEmsList(selectParams, "critic", criticId + "/publication", TypeFactory.defaultInstance().constructCollectionType(List.class,  Publication.class));
    }

}
