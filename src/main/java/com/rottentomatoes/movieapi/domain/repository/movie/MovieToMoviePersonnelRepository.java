package com.rottentomatoes.movieapi.domain.repository.movie;

import com.fasterxml.jackson.databind.type.TypeFactory;
import com.rottentomatoes.movieapi.domain.model.Movie;
import com.rottentomatoes.movieapi.domain.model.MovieCast;
import com.rottentomatoes.movieapi.domain.model.MoviePersonnel;
import com.rottentomatoes.movieapi.domain.repository.AbstractRepository;
import com.rottentomatoes.movieapi.domain.repository.ems.EmsClient;
import com.rottentomatoes.movieapi.enums.MovieCastRole;

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
public class MovieToMoviePersonnelRepository extends AbstractRepository implements RelationshipRepository<Movie, String, MoviePersonnel, String>, MetaRepository {

    private static final String CRITIC_TYPE = "criticType";
    private static final String TOP_CRITICS = "top";


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
    public MoviePersonnel findOneTarget(String movieId, String fieldName, RequestParams requestParams) {
        EmsClient emsClient = emsRouter.fetchEmsClientForEndpoint(this.getClass());
        Map<String, Object> selectParams = new HashMap<>();
        Integer limit = getActorsLimit(requestParams);
        if (limit != null) {
            selectParams.put("actorsLimit", limit);
        }
        List<MovieCast> personList = (List<MovieCast>) emsClient.callEmsList(selectParams, "movie", movieId + "/personnel", TypeFactory.defaultInstance().constructCollectionType(List.class,  MovieCast.class));

        // Load MoviePersonnel object manually;
        MoviePersonnel moviePersonnel = new MoviePersonnel();
        moviePersonnel.setId(movieId);

        for(MovieCast item : personList){
            if(item.getRole().equals(MovieCastRole.ACTORS.getCode())){
                moviePersonnel.getActors().add(item);
            }
            else if(item.getRole().equals(MovieCastRole.DIRECTORS.getCode())){
                moviePersonnel.getDirectors().add(item);
            }
            else if(item.getRole().equals(MovieCastRole.SCREENWRITERS.getCode())){
                moviePersonnel.getScreenwriters().add(item);
            }
            else if(item.getRole().equals(MovieCastRole.PRODUCERS.getCode())){
                moviePersonnel.getProducers().add(item);
            }
            else if(item.getRole().equals(MovieCastRole.EXECUTIVE_PRODUCERS.getCode())){
                moviePersonnel.getExecutiveProducers().add(item);
            }
        }

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
