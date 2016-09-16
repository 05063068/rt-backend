package com.rottentomatoes.movieapi.domain.repository;

import com.rottentomatoes.movieapi.domain.model.Person;
import com.rottentomatoes.movieapi.domain.model.PersonSupplementaryInfo;
import io.katharsis.queryParams.RequestParams;
import io.katharsis.repository.RelationshipRepository;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static com.rottentomatoes.movieapi.domain.repository.SqlParameterUtils.getMostRecentFriday;
import static com.rottentomatoes.movieapi.domain.repository.SqlParameterUtils.getTodayPST;
import static java.time.temporal.TemporalAdjusters.previousOrSame;

@SuppressWarnings("rawtypes")
@Component
public class PersonToPersonSupplementaryInfoRepository extends AbstractRepository implements RelationshipRepository<Person, String, PersonSupplementaryInfo, String> {

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
    public PersonSupplementaryInfo findOneTarget(String personId, String fieldName, RequestParams requestParams) {
        Map<String, Object> selectParams = new HashMap<>();
        PreEmsClient preEmsClient = new PreEmsClient(preEmsConfig);
        PersonSupplementaryInfo personSupplementaryInfo = (PersonSupplementaryInfo) preEmsClient.callPreEmsEntity(selectParams, "person", personId + "/supplement", PersonSupplementaryInfo.class);

        return personSupplementaryInfo;
    }

    @Override
    public Iterable<PersonSupplementaryInfo> findManyTargets(String s, String s2, RequestParams requestParams) {
        return null;
    }
}
