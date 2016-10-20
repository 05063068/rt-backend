package com.rottentomatoes.movieapi.domain.repository;

import java.util.HashMap;
import org.springframework.stereotype.Component;

import com.rottentomatoes.movieapi.domain.model.MovieCast;
import com.rottentomatoes.movieapi.domain.model.Person;

import io.katharsis.queryParams.RequestParams;
import io.katharsis.repository.RelationshipRepository;

@Component
public class MovieCastToPersonRepository extends AbstractRepository implements RelationshipRepository<MovieCast, String, Person, String> {

    @Override
    public void addRelations(MovieCast arg0, Iterable<String> arg1, String arg2) {

    }

    @Override
    public void removeRelations(MovieCast arg0, Iterable<String> arg1, String arg2) {
    }

    @Override
    public void setRelation(MovieCast arg0, String arg1, String arg2) {
    }

    @Override
    public void setRelations(MovieCast arg0, Iterable<String> arg1, String arg2) {
    }

    @Override
    public Person findOneTarget(String movieCastId, String fieldName, RequestParams requestParams) {
        EmsClient emsClient = emsConfig.fetchEmsClient("movie-personnel");
        Person person = (Person) emsClient.callEmsEntity(new HashMap<String,Object>(), "movie-personnel", movieCastId + "/person", Person.class);
        return person;
    }

    @Override
    public Iterable<Person> findManyTargets(String sourceId, String fieldName, RequestParams requestParams) {
        // TODO Auto-generated method stub
        return null;
    }
}
