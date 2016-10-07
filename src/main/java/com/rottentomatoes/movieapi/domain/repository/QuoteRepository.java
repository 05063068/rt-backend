package com.rottentomatoes.movieapi.domain.repository;

import com.rottentomatoes.movieapi.domain.model.Quote;
import com.rottentomatoes.movieapi.domain.model.Character;

import io.katharsis.queryParams.RequestParams;
import io.katharsis.repository.ResourceRepository;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class QuoteRepository extends AbstractRepository implements ResourceRepository<Quote, String> {

    @Override
    public <S extends Quote> S save(S entity) {
        return null;
    }

    @Override
    public void delete(String id) {

    }

    @Override
    public Quote findOne(String id, RequestParams requestParams) {

        Map<String, Object> selectParams = new HashMap<>();
        selectParams.put("id", id);

        Quote quote = talkSession.selectOne("com.rottentomatoes.movieapi.mappers.dbtalk.QuoteMapper.selectQuoteById", selectParams);
        List<Map<String,String>> lines = quote.getLines();

        // Backfill Character name from shared DB
        Set<String> characterIds = new HashSet<>();
        for(Map<String,String> line : lines){
            String characterId = line.get("characterId");
            if(characterId != null) {
                characterIds.add(characterId);
            }
        }

        selectParams = new HashMap<>();
        selectParams.put("ids", characterIds);
        if (!characterIds.isEmpty()) {
            Map<String,Character> characters = sqlSession.selectMap("com.rottentomatoes.movieapi.mappers.CharacterMapper.selectCharactersById", selectParams, "id");

            for(Map<String,String>  line : lines){
                String characterId = line.get("characterId");
                if(characterId != null) {
                    line.put("characterName",characters.get(characterId).getName());
                    line.remove("characterId");
                }
            }
        }

        return quote;
    }

    @Override
    public Iterable<Quote> findAll(RequestParams requestParams) {
        return null;
    }

    @Override
    public Iterable<Quote> findAll(Iterable<String> ids, RequestParams requestParams) {
        return null;
    }

}
