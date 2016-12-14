package com.rottentomatoes.movieapi.domain.repository.person;

import com.fasterxml.jackson.databind.type.TypeFactory;
import com.rottentomatoes.movieapi.domain.model.Person;
import com.rottentomatoes.movieapi.domain.model.MovieFilmographyItem;
import com.rottentomatoes.movieapi.domain.repository.AbstractRepository;
import com.rottentomatoes.movieapi.domain.ems.EmsClient;
import com.rottentomatoes.movieapi.utils.RepositoryUtils;
import io.katharsis.queryParams.RequestParams;
import io.katharsis.repository.RelationshipRepository;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("rawtypes")
@Component
public class PersonToMovieFilmographyItemRepository extends AbstractRepository implements RelationshipRepository<Person, String, MovieFilmographyItem, String> {

    @Override
    public void setRelation(Person person, String s, String s2) {

    }

    @Override
    public void setRelations(Person person, Iterable<String> iterable, String s) {

    }

    @Override
    public void addRelations(Person person, Iterable<String> iterable, String s) {

    }

    @Override
    public void removeRelations(Person person, Iterable<String> iterable, String s) {

    }

    @Override
    public MovieFilmographyItem findOneTarget(String personId, String fieldName, RequestParams requestParams) {
        return null;
    }

    @Override
    public Iterable<MovieFilmographyItem> findManyTargets(String personId, String fieldName, RequestParams requestParams) {
        Map<String, Object> selectParams = new HashMap<>();
        selectParams.put("limit", RepositoryUtils.getLimit(fieldName, requestParams));
        selectParams.put("offset", RepositoryUtils.getOffset(fieldName, requestParams));
        if (fieldName.equals("highestRated")) {
            selectParams.put("sort", "highest");
        } else if (fieldName.equals("lowestRated")) {
            selectParams.put("sort", "lowest");
        }

        EmsClient emsClient = emsRouter.fetchEmsClientForEndpoint(this.getClass());
        List<MovieFilmographyItem> filmography = (List<MovieFilmographyItem>)emsClient.callEmsList(selectParams, "person", personId + "/filmography", TypeFactory.defaultInstance().constructCollectionType(List.class,  MovieFilmographyItem.class));
        return filmography;

    }
}
