package com.rottentomatoes.movieapi.domain.repository.publication;

import com.fasterxml.jackson.databind.type.TypeFactory;
import com.rottentomatoes.movieapi.domain.meta.RelatedMetaDataInformation;
import com.rottentomatoes.movieapi.domain.model.Publication;
import com.rottentomatoes.movieapi.domain.model.Review;
import com.rottentomatoes.movieapi.domain.repository.AbstractRepository;
import com.rottentomatoes.movieapi.domain.ems.EmsClient;
import com.rottentomatoes.movieapi.utils.RepositoryUtils;
import io.katharsis.queryParams.RequestParams;
import io.katharsis.repository.MetaRepository;
import io.katharsis.repository.RelationshipRepository;
import io.katharsis.response.MetaInformation;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("rawtypes")
@Component
public class PublicationToReviewRepository extends AbstractRepository implements RelationshipRepository<Publication, String, Review, String>, MetaRepository {

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
    public Review findOneTarget(String publicationId, String fieldName, RequestParams requestParams) {
        return null;
    }

    @Override
    public Iterable<Review> findManyTargets(String publicationId, String fieldName, RequestParams requestParams) {
        Map<String, Object> selectParams = new HashMap<>();
        selectParams.put("limit", RepositoryUtils.getLimit(fieldName, requestParams));
        selectParams.put("offset", RepositoryUtils.getOffset(fieldName, requestParams));

        EmsClient emsClient = emsRouter.fetchEmsClientForEndpoint(this.getClass());
        List<Review> reviewList = (List<Review>)emsClient.callEmsList(selectParams, "publication", publicationId + "/review", TypeFactory.defaultInstance().constructCollectionType(List.class, Review.class));
        return reviewList;

    }

    @Override
    public MetaInformation getMetaInformation(Object o, Iterable iterable, RequestParams requestParams, Serializable publicationId) {
        Map<String, Object> selectParams = new HashMap<>();

        EmsClient emsClient = emsRouter.fetchEmsClientForEndpoint(this.getClass());
        RelatedMetaDataInformation metaData = (RelatedMetaDataInformation) emsClient.callEmsEntity(selectParams, "publication", publicationId + "/review/meta", RelatedMetaDataInformation.class);
        metaData.setRequestParams(requestParams);
        return metaData;
    }
}
