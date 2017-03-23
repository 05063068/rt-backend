package com.rottentomatoes.movieapi.domain.repository.vanity;

import com.rottentomatoes.movieapi.domain.clients.ems.EmsClient;
import com.rottentomatoes.movieapi.domain.model.TvVanityToken;
import com.rottentomatoes.movieapi.domain.repository.AbstractRepository;
import io.katharsis.queryParams.RequestParams;
import io.katharsis.repository.ResourceRepository;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class TvVanityTokenRepository extends AbstractRepository implements ResourceRepository<TvVanityToken, String> {

    @Override
    public <S extends TvVanityToken> S save(S entity) {
        return null;
    }

    @Override
    public void delete(String aLong) {
    }

    @Override
    public TvVanityToken findOne(String seriesToken, RequestParams requestParams) {
        Map<String, Object> selectParams = new HashMap<>();
        EmsClient emsClient = emsRouter.fetchEmsClientForEndpoint(this.getClass());

        String token = seriesToken;
        if(requestParams.getFilters() != null) {
            if (requestParams.getFilters().get("tvSeasonVanity") != null) {
                token += "/" + (String) requestParams.getFilters().get("tvSeasonVanity");
                if (requestParams.getFilters().containsKey("tvEpisodeVanity")) {
                    token += "/" + (String) requestParams.getFilters().get("tvEpisodeVanity");
                    String episodeId = (String) emsClient.callEmsEntity(selectParams, "tv/episode/vanity-url-to-id/", token, String.class);
                    return new TvVanityToken(token, null, null, episodeId);
                }
                String seasonId = (String) emsClient.callEmsEntity(selectParams, "tv/season/vanity-url-to-id/", token, String.class);
                return new TvVanityToken(token, null, seasonId, null);
            }
        }
        String seriesId = (String) emsClient.callEmsEntity(selectParams, "tv/series/vanity-url-to-id/", seriesToken, String.class);
        return new TvVanityToken(token, seriesId, null, null);
    }

    @Override

    public Iterable<TvVanityToken> findAll(RequestParams requestParams) {
        return null;
    }

    @Override
    public Iterable<TvVanityToken> findAll(Iterable<String> ids, RequestParams requestParams) {
        // TODO Auto-generated method stub
        return null;
    }
}
