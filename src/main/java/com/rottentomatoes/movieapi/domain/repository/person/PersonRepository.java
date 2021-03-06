package com.rottentomatoes.movieapi.domain.repository.person;

import java.util.HashMap;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.rottentomatoes.movieapi.domain.repository.AbstractRepository;
import com.rottentomatoes.movieapi.domain.clients.ems.EmsClient;
import com.rottentomatoes.movieapi.utils.SearchUtils;
import io.katharsis.queryParams.RequestParams;
import io.katharsis.repository.ResourceRepository;
import io.katharsis.response.MetaDataEnabledList;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import com.rottentomatoes.movieapi.domain.model.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.rottentomatoes.movieapi.utils.SearchUtils.loadSearchMeta;

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
        EmsClient emsClient = emsRouter.fetchEmsClientForEndpoint(this.getClass());
        Person person = (Person) emsClient.callEmsEntity(new HashMap<String,Object>(), "person", personId, Person.class);
        return person;
    }

    @Override
    public Iterable<Person> findAll(RequestParams requestParams) {
        EmsClient emsClient = emsRouter.fetchEmsClientForEndpoint(this.getClass());

        Map<String, Object> selectParams = new HashMap<>();
        MetaDataEnabledList<Person> persons = null;

        if (requestParams.getFilters() != null && requestParams.getFilters().get("search") != null) {
            List<Long> personIds;
            JsonNode json;

            if(requestParams.getFilters().get("search") instanceof Map){
                json = SearchUtils.callSearchService("actors", requestParams);
                personIds = new ArrayList<>();
                if (json != null) {
                    ArrayNode resultArr = (ArrayNode) json.path("results");
                    for (JsonNode movie : resultArr) {
                        personIds.add(Long.parseLong(movie.path("id").textValue()));
                    }
                    selectParams.put("ids", StringUtils.join(personIds, ","));
                }
            }
            else{
                throw new IllegalArgumentException("Invalid search query.");
            }

            //  Hydrate results
            if(personIds.size() > 0) {
                persons = new MetaDataEnabledList<>(((List<Person>) emsClient.callEmsList(selectParams, "person", null, TypeFactory.defaultInstance().constructCollectionType(List.class, Person.class))));
                persons.setMetaInformation(loadSearchMeta(json, requestParams));
            } else {
                persons = new MetaDataEnabledList<>(new ArrayList<>());
            }
        }

        return persons;
    }

    @Override
    public Iterable<Person> findAll(Iterable<String> ids, RequestParams requestParams) {
        return null;
    }
}
