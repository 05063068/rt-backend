package com.rottentomatoes.movieapi.domain.repository;

import com.rottentomatoes.movieapi.domain.model.Publication;
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
        selectParams.put("id", id);

        Publication publication = sqlSession.selectOne("com.rottentomatoes.movieapi.mappers.PublicationMapper.selectPublicationById", selectParams);
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

        List<Publication> publications = sqlSession.selectList("com.rottentomatoes.movieapi.mappers.PublicationMapper.selectAllPublications", selectParams);
        return publications;

    }

    @Override
    public Iterable<Publication> findAll(Iterable<String> ids, RequestParams requestParams) {
        return null;
    }

}
