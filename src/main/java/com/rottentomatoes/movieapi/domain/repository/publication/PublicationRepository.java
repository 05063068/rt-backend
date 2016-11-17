package com.rottentomatoes.movieapi.domain.repository.publication;

import com.fasterxml.jackson.databind.type.TypeFactory;
import com.rottentomatoes.movieapi.domain.model.Publication;
import com.rottentomatoes.movieapi.domain.repository.AbstractRepository;
import com.rottentomatoes.movieapi.domain.repository.ems.EmsClient;
import io.katharsis.queryParams.RequestParams;
import io.katharsis.repository.ResourceRepository;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class PublicationRepository extends AbstractRepository implements ResourceRepository<Publication, String> {

    @Override
    public <S extends Publication> S save(S entity) {
        return null;
    }

    @Override
    public void delete(String id) {

    }

    @Override
    public Publication findOne(String id, RequestParams requestParams) {

        Map<String, Object> selectParams = new HashMap<>();

        EmsClient emsClient = emsRouter.fetchEmsClientForEndpoint(this.getClass());
        Publication publication = (Publication) emsClient.callEmsEntity(selectParams, "publication", id, Publication.class);
        return publication;
    }

    @Override
    public Iterable<Publication> findAll(RequestParams requestParams) {
        // Return list of all Publications. Allow filter by last name


        Map<String, Object> selectParams = new HashMap<>();
        selectParams.put("limit", getLimit("", requestParams));
        selectParams.put("offset", getOffset("", requestParams));

        if(requestParams.getFilters() != null && requestParams.getFilters().containsKey("initial")){
            selectParams.put("initial",requestParams.getFilters().get("initial")+"%");
        }
        if(requestParams.getFilters() != null && requestParams.getFilters().containsKey("search")){
            selectParams.put("search","%"+requestParams.getFilters().get("search")+"%");
        }

        EmsClient emsClient = emsRouter.fetchEmsClientForEndpoint(this.getClass());
        List<Publication> publications = (List<Publication>)emsClient.callEmsList(selectParams, "publication", null, TypeFactory.defaultInstance().constructCollectionType(List.class,  Publication.class));

        return publications;

    }

    @Override
    public Iterable<Publication> findAll(Iterable<String> ids, RequestParams requestParams) {
        return null;
    }

}
