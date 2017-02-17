package com.rottentomatoes.movieapi.domain.repository.franchise;

import com.fasterxml.jackson.databind.type.TypeFactory;
import com.rottentomatoes.movieapi.domain.ems.EmsClient;
import com.rottentomatoes.movieapi.domain.meta.RelatedMetaDataInformation;
import com.rottentomatoes.movieapi.domain.model.Franchise;
import com.rottentomatoes.movieapi.domain.model.Image;
import com.rottentomatoes.movieapi.domain.repository.AbstractRepository;
import com.rottentomatoes.movieapi.utils.RepositoryUtils;
import io.katharsis.queryParams.RequestParams;
import io.katharsis.repository.MetaRepository;
import io.katharsis.repository.RelationshipRepository;
import io.katharsis.response.MetaDataEnabledList;
import io.katharsis.response.MetaInformation;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("rawtypes")
@Component
public class FranchiseToImageRepository extends AbstractRepository implements RelationshipRepository<Franchise, String, Image, String>, MetaRepository {

    @Override
    public void setRelation(Franchise source, String targetId, String fieldName) {

    }

    @Override
    public void setRelations(Franchise source, Iterable<String> targetIds, String fieldName) {

    }

    @Override
    public void addRelations(Franchise source, Iterable<String> targetIds, String fieldName) {

    }

    @Override
    public void removeRelations(Franchise source, Iterable<String> targetIds, String fieldName) {

    }

    @Override
    public Image findOneTarget(String franchiseId, String fieldName, RequestParams requestParams) {
        return null;
    }

    @Override
    public Iterable<Image> findManyTargets(String franchiseId, String fieldName, RequestParams requestParams) {
        Map<String, Object> selectParams = new HashMap<>();
        selectParams.put("limit", RepositoryUtils.getLimit(fieldName, requestParams));
        selectParams.put("offset", RepositoryUtils.getOffset(fieldName, requestParams));
        EmsClient emsClient = emsRouter.fetchEmsClientForEndpoint(this.getClass());
        List<Image> rawImageList = (List<Image>) emsClient.callEmsList(selectParams, "franchise", franchiseId + "/images", TypeFactory.defaultInstance().constructCollectionType(List.class,  Image.class));
        return new MetaDataEnabledList(rawImageList);
    }

    @Override
    public MetaInformation getMetaInformation(Object root, Iterable resources, Serializable castedResourceId, String fieldName, RequestParams requestParams) {
        Map<String, Object> selectParams = new HashMap<>();
        // arbitrarily high limit
        selectParams.put("limit", 10000);

        EmsClient emsClient = emsRouter.fetchEmsClientForEndpoint(this.getClass());

//        RelatedMetaDataInformation metaData = (RelatedMetaDataInformation) emsClient.callEmsEntity(selectParams, "franchise", castedResourceId + "/images/meta", RelatedMetaDataInformation.class);
//        if (metaData != null && root instanceof RelationshipRepository) {
//            metaData.setRequestParams(requestParams);
//        }

        List<Image> rawImageList = (List<Image>)emsClient.callEmsList(selectParams, "franchise", castedResourceId + "/images", TypeFactory.defaultInstance().constructCollectionType(List.class,  Image.class));
        RelatedMetaDataInformation metaData = null;
        if (rawImageList != null) {
            metaData = new RelatedMetaDataInformation();
            metaData.setTotalCount(rawImageList.size());
            if (root instanceof RelationshipRepository) {
                metaData.setRequestParams(requestParams);
            }
        }

        return metaData;
    }
}
