package com.rottentomatoes.movieapi.domain.repository;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class AbstractRepository {
	@Autowired
	@Qualifier("sqlSession")
	protected SqlSessionTemplate sqlSession;

}
