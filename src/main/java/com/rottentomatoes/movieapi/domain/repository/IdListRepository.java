package com.rottentomatoes.movieapi.domain.repository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.time.temporal.TemporalAdjusters.*;

import org.springframework.stereotype.Component;

import com.rottentomatoes.movieapi.domain.model.IdList;
import com.rottentomatoes.movieapi.domain.model.Movie;
import com.rottentomatoes.movieapi.domain.model.MovieList;

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
                IdList retval = new IdList();
                retval.setId(listId);
                
                Map<String, Object> selectParams = new HashMap<>();
                List<String> ids = sqlSession.selectList("com.rottentomatoes.movieapi.mappers.IdListMapper.selectSitemapMovieIds", selectParams);
                retval.setIdsInList(ids);
                return retval; 
                
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
