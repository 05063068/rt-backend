package com.rottentomatoes.movieapi.domain.repository;

import java.util.HashMap;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.rottentomatoes.movieapi.domain.model.Franchise;
import com.rottentomatoes.movieapi.domain.model.Movie;
import com.rottentomatoes.movieapi.search.SearchQuery;
import io.katharsis.queryParams.RequestParams;
import io.katharsis.repository.ResourceRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import com.rottentomatoes.movieapi.domain.model.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class PersonRepository extends AbstractRepository implements ResourceRepository<Person, String> {

    @Override
    public void delete(String aString) {

    }

    @Override
    public <S extends Person> S save(S arg0) {
        return null;
    }

    @Override
    public Person findOne(String personId, RequestParams requestParams) {
        EmsClient emsClient = emsConfig.fetchEmsClient("person");
        Person person = (Person) emsClient.callEmsEntity(new HashMap<String,Object>(), "person", personId, Person.class);
        return person;
    }

    @Override
    public Iterable<Person> findAll(RequestParams requestParams) {
        PreEmsClient preEmsClient = new PreEmsClient<Person>(preEmsConfig);

        Map<String, Object> selectParams = new HashMap<>();
        List<Person> persons;
        List<Long> personIds = new ArrayList<>();

        if (requestParams.getFilters() != null && requestParams.getFilters().get("search") != null) {

            if(requestParams.getFilters().get("search") instanceof Map){
                Map<String, Object> searchObj = (Map<String, Object>) requestParams.getFilters().get("search");

                SearchQuery q = new SearchQuery("actors", searchObj);
                JsonNode json = q.execute();
                ArrayNode resultArr = (ArrayNode) json.path("results");
                personIds = new ArrayList<>();
                for (JsonNode movie : resultArr) {
                    personIds.add(Long.parseLong(movie.path("id").textValue()));
                }
                selectParams.put("ids", StringUtils.join(personIds,","));
            }
            else{
                throw new IllegalArgumentException("Invalid search query.");
            }
        }

        //  Hydrate results
        if(personIds.size() > 0) {
            persons = (List<Person>) preEmsClient.callPreEmsList(selectParams, "person", null, TypeFactory.defaultInstance().constructCollectionType(List.class, Person.class));
        }
        else{
            persons = new ArrayList<>();
        }
        return persons;
    }

    @Override
    public Iterable<Person> findAll(Iterable<String> ids, RequestParams requestParams) {
        return null;
    }
}
