package com.rottentomatoes.movieapi.domain.repository;

import com.fasterxml.jackson.databind.type.TypeFactory;
import com.rottentomatoes.movieapi.domain.model.MovieCast;
import com.rottentomatoes.movieapi.domain.model.MoviePersonnel;
import com.rottentomatoes.movieapi.domain.model.TvEpisode;
import com.rottentomatoes.movieapi.domain.model.TvSeason;
import io.katharsis.queryParams.RequestParams;
import io.katharsis.repository.MetaRepository;
import io.katharsis.repository.RelationshipRepository;
import io.katharsis.response.MetaInformation;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("rawtypes")
@Component
public class TvSeasonToMoviePersonnelRepository extends AbstractRepository implements RelationshipRepository<TvSeason, String, MoviePersonnel, String>, MetaRepository {

    @Override
    public void addRelations(TvSeason arg0, Iterable<String> arg1, String arg2) {
    }

    @Override
    public void removeRelations(TvSeason arg0, Iterable<String> arg1, String arg2) {
    }

    @Override
    public void setRelation(TvSeason arg0, String arg1, String arg2) {
    }

    @Override
    public void setRelations(TvSeason arg0, Iterable<String> arg1, String arg2) {
    }

    @Override
    public MoviePersonnel findOneTarget(String tvSeasonId, String fieldName, RequestParams requestParams) {
        Map<String, Object> selectParams = new HashMap<>();
        Integer limit = getActorsLimit(requestParams);
        if (limit != null) {
            selectParams.put("actorsLimit", limit);
        }

        EmsClient emsClient = emsRouter.fetchEmsClientForEndpoint(this.getClass());
        List<MovieCast> castList = (List<MovieCast>) emsClient.callEmsIdList(selectParams, "tv/season", tvSeasonId + "/cast", "tv/cast", TypeFactory.defaultInstance().constructCollectionType(List.class, MovieCast.class));
        MoviePersonnel moviePersonnel = new MoviePersonnel(tvSeasonId, castList);

        return moviePersonnel;
    }


    // Note: This is not the regular limit mechanism but a special case for limiting the number of actors returned
    // without affecting other personnel types
    protected Integer getActorsLimit(RequestParams requestParams) {
        if (requestParams != null) {
            if (requestParams.getFilters() != null && requestParams.getFilters().containsKey("actorsLimit")) {
                return (Integer) requestParams.getFilters().get("actorsLimit");
            }
        }
        return null;
    }


    @Override
    public Iterable<MoviePersonnel> findManyTargets(String s, String s2, RequestParams requestParams) {
        return null;
    }

    @Override
    public MetaInformation getMetaInformation(Object root, Iterable resources, RequestParams requestParams, Serializable castedResourceId) {
        return null;
    }
}
