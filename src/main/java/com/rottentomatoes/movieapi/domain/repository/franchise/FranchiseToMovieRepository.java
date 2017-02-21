package com.rottentomatoes.movieapi.domain.repository.franchise;

import com.fasterxml.jackson.databind.type.TypeFactory;
import com.rottentomatoes.movieapi.domain.model.Franchise;
import com.rottentomatoes.movieapi.domain.model.Movie;
import com.rottentomatoes.movieapi.domain.model.TvSeries;
import com.rottentomatoes.movieapi.domain.repository.AbstractRepository;
import com.rottentomatoes.movieapi.domain.ems.EmsClient;
import com.rottentomatoes.movieapi.domain.repository.movie.MovieRepository;
import com.rottentomatoes.movieapi.utils.RepositoryUtils;
import io.katharsis.queryParams.RequestParams;
import io.katharsis.repository.RelationshipRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class FranchiseToMovieRepository extends AbstractRepository implements RelationshipRepository<Franchise, String, Movie, String> {

    @Override
    public void addRelations(Franchise arg0, Iterable<String> arg1, String arg2) {
    }

    @Override
    public void removeRelations(Franchise arg0, Iterable<String> arg1, String arg2) {
    }

    @Override
    public void setRelation(Franchise arg0, String Movie, String arg2) {
    }

    @Override
    public void setRelations(Franchise arg0, Iterable<String> arg1, String arg2) {
    }

    @Override
    public Movie findOneTarget(String id, String fieldName, RequestParams requestParams) {
        Map<String, Object> selectParams = new HashMap<>();
        EmsClient emsClient = emsRouter.fetchEmsClientForEndpoint(this.getClass());

        if (fieldName.equals("topMovie")) {
            String movieId = (String) emsClient.callEmsEntity(selectParams, "franchise", id + "/top-movie", String.class);

            if (movieId != null && !movieId.equals("")) {
                emsClient = emsRouter.fetchEmsClientForEndpoint(MovieRepository.class);
                selectParams.put("country", RepositoryUtils.getCountry(requestParams).getCountryCode());
                return (Movie) emsClient.callEmsEntity(selectParams, "movie", movieId, Movie.class);
            }
        }
        return null;
    }

    @Override
    public Iterable<Movie> findManyTargets(String franchiseId, String fieldName, RequestParams requestParams) {
        Map<String, Object> selectParams = new HashMap<>();
        selectParams.put("limit", RepositoryUtils.getLimit(fieldName, requestParams));
        selectParams.put("offset", RepositoryUtils.getOffset(fieldName, requestParams));

        EmsClient emsClient = emsRouter.fetchEmsClientForEndpoint(this.getClass());
        List<String> movieIds = (List<String>) emsClient.callEmsList(selectParams, "franchise", franchiseId + "/movies",
                TypeFactory.defaultInstance().constructCollectionType(List.class, String.class));

        emsClient = emsRouter.fetchEmsClientForEndpoint(MovieRepository.class);
        if (movieIds != null && movieIds.size() > 0) {
            selectParams.put("ids", StringUtils.join(movieIds, ","));
            List<Movie> movieList = (List<Movie>) emsClient.callEmsList(selectParams, "movie", null,
                    TypeFactory.defaultInstance().constructCollectionType(List.class, Movie.class));
            if (movieList != null && movieList.size() > 0) {
                Collections.sort(movieList,
                        Comparator.comparing(item -> movieIds.indexOf(((Movie) item).getId())));
                Collections.reverse(movieList);
            }
            return movieList;
        }
        return null;
    }
}
