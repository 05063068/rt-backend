package com.rottentomatoes.movieapi.domain.repository;

import com.fasterxml.jackson.databind.type.TypeFactory;
import com.rottentomatoes.movieapi.domain.meta.RelatedMetaDataInformation;
import com.rottentomatoes.movieapi.domain.model.Publication;
import com.rottentomatoes.movieapi.domain.model.Critic;
import io.katharsis.queryParams.RequestParams;
import io.katharsis.repository.MetaRepository;
import io.katharsis.repository.RelationshipRepository;
import io.katharsis.response.MetaInformation;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.rottentomatoes.movieapi.utils.RepositoryUtils.getLimit;
import static com.rottentomatoes.movieapi.utils.RepositoryUtils.getOffset;

@SuppressWarnings("rawtypes")
@Component
public class PublicationToCriticRepository extends AbstractRepository implements RelationshipRepository<Publication, String, Critic, String> {

    @Override
    public void setRelation(Publication publication, String s, String s2) {

    }

    @Override
    public void setRelations(Publication publication, Iterable<String> iterable, String s) {

    }

    @Override
    public void addRelations(Publication publication, Iterable<String> iterable, String s) {

    }


    @Override
    public void removeRelations(Publication publication, Iterable<String> iterable, String s) {

    }

    @Override
    public Critic findOneTarget(String publicationId, String fieldName, RequestParams requestParams) {
        return null;
    }

    @Override
    public Iterable<Critic> findManyTargets(String publicationId, String fieldName, RequestParams requestParams) {
        Map<String, Object> selectParams = new HashMap<>();
        selectParams.put("limit", getLimit(fieldName, requestParams));
        selectParams.put("offset", getOffset(fieldName, requestParams));

        PreEmsClient preEmsClient = new PreEmsClient<List<Critic>>(preEmsConfig);
        List<Critic> criticList = (List<Critic>)preEmsClient.callPreEmsList(selectParams, "publication", publicationId + "/critic", TypeFactory.defaultInstance().constructCollectionType(List.class, Critic.class));
        return criticList;

    }

}
