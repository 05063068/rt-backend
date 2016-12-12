package com.rottentomatoes.movieapi.domain.repository.person;

import com.fasterxml.jackson.databind.type.TypeFactory;
import com.rottentomatoes.movieapi.domain.ems.EmsClient;
import com.rottentomatoes.movieapi.domain.meta.RelatedMetaDataInformation;
import com.rottentomatoes.movieapi.domain.model.Movie;
import com.rottentomatoes.movieapi.domain.model.Person;
import com.rottentomatoes.movieapi.domain.model.Quote;
import com.rottentomatoes.movieapi.domain.repository.AbstractRepository;
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

@Component
public class PersonToQuoteRepository extends AbstractRepository implements RelationshipRepository<Person, String, Quote, String>, MetaRepository {

    @Override
    public void addRelations(Person arg0, Iterable<String> arg1, String arg2) {
    }

    @Override
    public void removeRelations(Person arg0, Iterable<String> arg1, String arg2) {
    }

    @Override
    public void setRelation(Person arg0, String arg1, String arg2) {
    }

    @Override
    public void setRelations(Person arg0, Iterable<String> arg1, String arg2) {
    }

    @Override
    public Quote findOneTarget(String movieId, String fieldName, RequestParams requestParams) {
        return null;
    }

    @Override
    public Iterable<Quote> findManyTargets(String personId, String fieldName, RequestParams requestParams) {
        Map<String, Object> selectParams = new HashMap<>();
        selectParams.put("limit", RepositoryUtils.getLimit(fieldName, requestParams));
        selectParams.put("offset", RepositoryUtils.getOffset(fieldName, requestParams));

        EmsClient emsClient = emsRouter.fetchEmsClientForEndpoint(this.getClass());
        Iterable<Quote> quotes = (Iterable<Quote>) emsClient.callEmsList(selectParams, "person", personId + "/quote", TypeFactory.defaultInstance().constructCollectionType(List.class,  Quote.class));

        return quotes;
    }

    @Override
    public MetaInformation getMetaInformation(Object root, Iterable resources, RequestParams requestParams, Serializable castedResourceId) {
        Map<String, Object> selectParams = new HashMap<>();

        EmsClient emsClient = emsRouter.fetchEmsClientForEndpoint(this.getClass());
        RelatedMetaDataInformation metaData = (RelatedMetaDataInformation) emsClient.callEmsEntity(selectParams, "person", castedResourceId + "/quote/meta", RelatedMetaDataInformation.class);
        if (root instanceof RelationshipRepository) {
            metaData.setRequestParams(requestParams);
        }
        return metaData;
    }
}
