package com.rottentomatoes.movieapi.domain.repository.person;

import com.fasterxml.jackson.databind.type.TypeFactory;
import com.rottentomatoes.movieapi.domain.ems.EmsClient;
import com.rottentomatoes.movieapi.domain.model.MovieFilmographyItem;
import com.rottentomatoes.movieapi.domain.model.Person;
import com.rottentomatoes.movieapi.domain.model.TvFilmographyItem;
import com.rottentomatoes.movieapi.domain.repository.AbstractRepository;
import io.katharsis.queryParams.RequestParams;
import io.katharsis.repository.RelationshipRepository;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("rawtypes")
@Component
public class PersonToTvFilmographyItemRepository extends AbstractRepository implements RelationshipRepository<Person, String, TvFilmographyItem, String> {

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
    public TvFilmographyItem findOneTarget(String personId, String fieldName, RequestParams requestParams) {
        return null;
    }

    @Override
    public Iterable<TvFilmographyItem> findManyTargets(String s, String s2, RequestParams requestParams) {
        Map<String, Object> selectParams = new HashMap<>();

        EmsClient emsClient = emsRouter.fetchEmsClientForEndpoint(this.getClass());
        List<TvFilmographyItem> filmography = (List<TvFilmographyItem>)emsClient.callEmsList(selectParams, "person", s + "/tv-filmography", TypeFactory.defaultInstance().constructCollectionType(List.class,  MovieFilmographyItem.class));
        // TODO: metadata for filmography
        return filmography;

    }
}
