package com.rottentomatoes.movieapi.domain.repository.publication;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.katharsis.response.MetaDataEnabledList;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.type.TypeFactory;
import com.rottentomatoes.movieapi.domain.clients.ems.EmsClient;
import com.rottentomatoes.movieapi.domain.model.meta.RelatedMetaDataInformation;
import com.rottentomatoes.movieapi.domain.model.Publication;
import com.rottentomatoes.movieapi.domain.model.Review;
import com.rottentomatoes.movieapi.domain.repository.AbstractRepository;
import com.rottentomatoes.movieapi.utils.RepositoryUtils;

import io.katharsis.queryParams.RequestParams;
import io.katharsis.repository.MetaRepository;
import io.katharsis.repository.RelationshipRepository;
import io.katharsis.response.MetaInformation;

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

    @SuppressWarnings("unchecked")
    @Override
    public Iterable<Review> findManyTargets(String publicationId, String fieldName, RequestParams requestParams) {
        Map<String, Object> selectParams = new HashMap<>();
        selectParams.put("limit", RepositoryUtils.getLimit(fieldName, requestParams));
        selectParams.put("offset", RepositoryUtils.getOffset(fieldName, requestParams));

        EmsClient emsClient;
        MetaDataEnabledList<Review> reviewList;

        switch (fieldName) {
            case "tvReviews":
                emsClient = emsRouter.fetchEmsClientForPath("publication/" + fieldName);
                reviewList = (MetaDataEnabledList<Review>) emsClient.callEmsIdList(selectParams, "tv/publication", publicationId + "/review", "tv/review", TypeFactory.defaultInstance().constructCollectionType(MetaDataEnabledList.class, Review.class));
                break;
            case "reviews":
            default:
                emsClient = emsRouter.fetchEmsClientForEndpoint(this.getClass());
                reviewList = (MetaDataEnabledList<Review>) emsClient.callEmsList(selectParams, "publication", publicationId + "/review", TypeFactory.defaultInstance().constructCollectionType(MetaDataEnabledList.class, Review.class));
                break;
        }
        return reviewList;
    }

    @Override
    public MetaInformation getMetaInformation(Object root, Iterable resources, Serializable castedResourceId, String fieldName, RequestParams requestParams) {
        Map<String, Object> selectParams = new HashMap<>();
        EmsClient emsClient;
        RelatedMetaDataInformation metaData;

        switch (fieldName) {
            case "tvReviews":
                selectParams.put("limit", 10000);
                emsClient = emsRouter.fetchEmsClientForPath("publication/" + fieldName);
                List idList = (List<Integer>) emsClient.callEmsList(selectParams, "tv/publication", castedResourceId.toString() + "/review", TypeFactory.defaultInstance().constructCollectionType(List.class, Integer.class));

                metaData = new RelatedMetaDataInformation();
                if (idList != null) {
                    metaData.setTotalCount(idList.size());
                }
                break;
            case "reviews":
            default:
                emsClient = emsRouter.fetchEmsClientForEndpoint(this.getClass());
                metaData = (RelatedMetaDataInformation) emsClient.callEmsEntity(selectParams, "publication", castedResourceId.toString() + "/review/meta", RelatedMetaDataInformation.class);
        }

        if (metaData != null && root instanceof RelationshipRepository) {
            metaData.setRequestParams(requestParams);
        }
        return metaData;
    }
}
