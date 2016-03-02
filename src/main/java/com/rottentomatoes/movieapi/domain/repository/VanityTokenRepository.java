package com.rottentomatoes.movieapi.domain.repository;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.rottentomatoes.movieapi.domain.model.VanityToken;

import io.katharsis.queryParams.RequestParams;
import io.katharsis.repository.ResourceRepository;

@Component
public class VanityTokenRepository extends AbstractRepository implements ResourceRepository<VanityToken, String> {
    
    @Override
    public <S extends VanityToken> S save(S entity) {
        return null;
    }

    @Override
    public void delete(String aLong) {
    }

	@Override
	public VanityToken findOne(String vanityToken, RequestParams requestParams) {
		Map<String, Object> selectParams = new HashMap<>();      
		selectParams.put("vanityToken", vanityToken);
        VanityToken token = sqlSession.selectOne("com.rottentomatoes.movieapi.mappers.VanityTokenMapper.selectVanityToken", selectParams);
        return token;
	}

	@Override
	
	public Iterable<VanityToken> findAll(RequestParams requestParams) {
		return null;
	}

	@Override
	public Iterable<VanityToken> findAll(Iterable<String> ids, RequestParams requestParams) {
		// TODO Auto-generated method stub
		return null;
	}
}
