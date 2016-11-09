package com.rottentomatoes.movieapi.domain.repository;

import com.fasterxml.jackson.databind.type.TypeFactory;
import com.rottentomatoes.movieapi.domain.model.Movie;
import com.rottentomatoes.movieapi.domain.model.MovieCast;
import com.rottentomatoes.movieapi.domain.model.MoviePersonnel;
import com.rottentomatoes.movieapi.domain.model.Review;
import com.rottentomatoes.movieapi.domain.model.ReviewInfo;
import com.rottentomatoes.movieapi.domain.model.TvEpisode;
import com.rottentomatoes.movieapi.enums.MovieCastRole;
import io.katharsis.queryParams.RequestParams;
import io.katharsis.repository.MetaRepository;
import io.katharsis.repository.RelationshipRepository;
import io.katharsis.response.MetaInformation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("rawtypes")
@Component
public class TvEpisodeToMoviePersonnelRepository extends AbstractRepository implements RelationshipRepository<TvEpisode, String, MoviePersonnel, String>, MetaRepository {

    private static final String CRITIC_TYPE = "criticType";
    private static final String TOP_CRITICS = "top";


    @Override
    public void addRelations(TvEpisode arg0, Iterable<String> arg1, String arg2) {
    }

    @Override
    public void removeRelations(TvEpisode arg0, Iterable<String> arg1, String arg2) {
    }

    @Override
    public void setRelation(TvEpisode arg0, String arg1, String arg2) {
    }

    @Override
    public void setRelations(TvEpisode arg0, Iterable<String> arg1, String arg2) {
    }

    @Override
    public MoviePersonnel findOneTarget(String tvEpisodeId, String fieldName, RequestParams requestParams) {
        Map<String, Object> selectParams = new HashMap<>();
        Integer limit = getActorsLimit(requestParams);
        if (limit != null) {
            selectParams.put("actorsLimit", limit);
        }

        EmsClient emsClient = emsConfig.fetchEmsClientForEndpoint("tv/episode");
        List<Integer> idList = (List<Integer>) emsClient.callEmsList(selectParams, "tv/episode", tvEpisodeId + "/cast", TypeFactory.defaultInstance().constructCollectionType(List.class, Integer.class));

        if (idList != null && idList.size() > 0) {
            String castIds = StringUtils.join(idList, ",");
            List<MovieCast> castList = (List<MovieCast>) emsClient.callEmsList(selectParams, "tv/cast", castIds, TypeFactory.defaultInstance().constructCollectionType(List.class, MovieCast.class));

            // Load MoviePersonnel object manually;
            MoviePersonnel moviePersonnel = new MoviePersonnel();
            moviePersonnel.setId(tvEpisodeId);

            for (MovieCast item : castList) {
                if (item.getRole().equals(MovieCastRole.ACTORS.getCode())) {
                    moviePersonnel.getActors().add(item);
                } else if (item.getRole().equals(MovieCastRole.DIRECTORS.getCode())) {
                    moviePersonnel.getDirectors().add(item);
                } else if (item.getRole().equals(MovieCastRole.SCREENWRITERS.getCode())) {
                    moviePersonnel.getScreenwriters().add(item);
                } else if (item.getRole().equals(MovieCastRole.PRODUCERS.getCode())) {
                    moviePersonnel.getProducers().add(item);
                } else if (item.getRole().equals(MovieCastRole.EXECUTIVE_PRODUCERS.getCode())) {
                    moviePersonnel.getExecutiveProducers().add(item);
                }
            }
            return moviePersonnel;
        }
        return null;
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
