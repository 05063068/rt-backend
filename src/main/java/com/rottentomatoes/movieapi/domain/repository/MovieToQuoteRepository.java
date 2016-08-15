package com.rottentomatoes.movieapi.domain.repository;

import com.rottentomatoes.movieapi.domain.model.Character;
import com.rottentomatoes.movieapi.domain.model.Quote;
import com.rottentomatoes.movieapi.domain.model.Movie;
import io.katharsis.queryParams.RequestParams;
import io.katharsis.repository.RelationshipRepository;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class MovieToQuoteRepository extends AbstractRepository implements RelationshipRepository<Movie, String, Quote, String> {

    @Override
    public void addRelations(Movie arg0, Iterable<String> arg1, String arg2) {
    }

    @Override
    public void removeRelations(Movie arg0, Iterable<String> arg1, String arg2) {
    }

    @Override
    public void setRelation(Movie arg0, String arg1, String arg2) {
    }

    @Override
    public void setRelations(Movie arg0, Iterable<String> arg1, String arg2) {
    }

    @Override
    public Quote findOneTarget(String movieId, String fieldName, RequestParams requestParams) {
        return null;
    }

    @Override
    public Iterable<Quote> findManyTargets(String movieId, String fieldName, RequestParams requestParams) {
        Map<String, Object> selectParams = new HashMap<>();
        selectParams.put("movie_id", movieId);
        selectParams.put("limit", getLimit(fieldName, requestParams));
        selectParams.put("offset", getOffset(fieldName, requestParams));


        Iterable<Quote> quotes = talkSession.selectList("com.rottentomatoes.movieapi.mappers.dbtalk.QuoteMapper.selectQuotesByMovie", selectParams);
        // Backfill Character name from shared DB
        Set<String> characterIds = new HashSet<>();

        for(Quote q : quotes) {
            List<Map<String,String>> lines = q.getLines();
            for (Map<String,String> line : lines) {
                String characterId = line.get("characterId");
                if (characterId != null) {
                    characterIds.add(characterId);
                }
            }
        }

        selectParams = new HashMap<>();
        selectParams.put("ids", characterIds);
        Map<String,Character> characters = sqlSession.selectMap("com.rottentomatoes.movieapi.mappers.CharacterMapper.selectCharactersById", selectParams, "id");

        for(Quote q : quotes) {
            List<Map<String,String>> lines = q.getLines();
            for (Map<String,String> line : lines) {
                String characterId = line.get("characterId");
                if (characterId != null) {
                    line.put("characterName", characters.get(characterId).getName());
                    line.remove("characterId");
                }
            }
        }

        return quotes;
    }
}
