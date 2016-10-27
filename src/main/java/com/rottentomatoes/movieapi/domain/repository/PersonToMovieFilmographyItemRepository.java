package com.rottentomatoes.movieapi.domain.repository;

import com.fasterxml.jackson.databind.type.TypeFactory;
import com.rottentomatoes.movieapi.domain.model.Person;
import com.rottentomatoes.movieapi.domain.model.MovieFilmographyItem;
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
    public Iterable<MovieFilmographyItem> findManyTargets(String s, String s2, RequestParams requestParams) {
        Map<String, Object> selectParams = new HashMap<>();

        EmsClient emsClient = emsConfig.fetchEmsClientForEndpoint("person");
        List<MovieFilmographyItem> filmography = (List<MovieFilmographyItem>)emsClient.callEmsList(selectParams, "person", s + "/filmography", TypeFactory.defaultInstance().constructCollectionType(List.class,  MovieFilmographyItem.class));
        return filmography;

    }
}
