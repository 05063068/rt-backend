package com.rottentomatoes.movieapi.domain.repository;

import java.util.HashMap;
import java.util.Map;

import com.rottentomatoes.movieapi.domain.ems.EmsClient;
import com.rottentomatoes.movieapi.utils.RepositoryUtils;
import org.springframework.stereotype.Component;

import com.rottentomatoes.movieapi.domain.model.IdList;

import io.katharsis.queryParams.RequestParams;
import io.katharsis.repository.ResourceRepository;
import io.katharsis.resource.exception.ResourceNotFoundException;

@Component
public class IdListRepository extends AbstractRepository implements ResourceRepository<IdList, String> {

    @Override
    public <S extends IdList> S save(S entity) {
        return null;
    }

    @Override
    public void delete(String aLong) {
    }

    @Override
    public IdList findOne(String listId, RequestParams requestParams) {

        switch (listId) {
            case "sitemap-movie-ids":

                Map<String, Object> selectParams = new HashMap<>();
                Map<String, Object> filters = requestParams.getFilters();
                Integer minReviews = null;
                Integer minRatings = null;
                if (filters != null) {
                    minReviews = (Integer) filters.get("minReviews");
                    minRatings = (Integer) filters.get("minRatings");
                }
                if (minReviews == null) {
                    minReviews = 5;
                }
                if (minRatings == null) {
                    minRatings = 300;
                }

                String fieldName = "ids"; // Pass in idsLimit, idsOffset
                selectParams.put("limit", RepositoryUtils.getLimit(fieldName, requestParams));
                selectParams.put("offset", RepositoryUtils.getOffset(fieldName, requestParams));
                selectParams.put("minReviews", minReviews);
                selectParams.put("minRatings", minRatings);
                EmsClient emsClient = emsRouter.fetchEmsClientForEndpoint(this.getClass());
                IdList idList = (IdList) emsClient.callEmsEntity(selectParams, "site-map", "movie-ids", IdList.class);
                return idList;


            default:
                throw new ResourceNotFoundException("Invalid list type");
        }
    }

    @Override
    public Iterable<IdList> findAll(RequestParams requestParams) {
        return null;
    }

    @Override
    public Iterable<IdList> findAll(Iterable<String> ids, RequestParams requestParams) {
        return null;
    }
}
