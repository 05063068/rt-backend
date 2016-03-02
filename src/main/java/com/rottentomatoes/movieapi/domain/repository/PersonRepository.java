package com.rottentomatoes.movieapi.domain.repository;

import io.katharsis.queryParams.RequestParams;
import io.katharsis.repository.ResourceRepository;

import org.springframework.stereotype.Component;

import com.rottentomatoes.movieapi.domain.model.Person;

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
        Person person = sqlSession.selectOne("com.rottentomatoes.movieapi.mappers.PersonMapper.selectPersonById", personId);
        return person;
	}

	@Override
	public Iterable<Person> findAll(RequestParams requestParams) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<Person> findAll(Iterable<String> ids, RequestParams requestParams) {
		// TODO Auto-generated method stub
		return null;
	}
}
