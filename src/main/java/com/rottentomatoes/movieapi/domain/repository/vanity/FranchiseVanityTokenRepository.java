package com.rottentomatoes.movieapi.domain.repository.vanity;

import com.rottentomatoes.movieapi.domain.clients.ems.EmsClient;
import com.rottentomatoes.movieapi.domain.model.FranchiseVanityToken;
import com.rottentomatoes.movieapi.domain.repository.AbstractRepository;
import io.katharsis.queryParams.RequestParams;
import io.katharsis.repository.ResourceRepository;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class FranchiseVanityTokenRepository extends AbstractRepository implements ResourceRepository<FranchiseVanityToken, String> {

    @Override
    public <S extends FranchiseVanityToken> S save(S entity) {
        return null;
    }

    @Override
    public void delete(String aLong) {
    }

    @Override
    public FranchiseVanityToken findOne(String vanityToken, RequestParams requestParams) {
        Map<String, Object> selectParams = new HashMap<>();
        EmsClient emsClient = emsRouter.fetchEmsClientForEndpoint(this.getClass());
        String franchiseId = (String) emsClient.callEmsEntity(selectParams, "franchise/vanity-url-to-id/", vanityToken, String.class);
        FranchiseVanityToken token = new FranchiseVanityToken();
        token.setId(vanityToken);
        token.setFranchiseId(franchiseId);
        return token;
    }

    @Override

    public Iterable<FranchiseVanityToken> findAll(RequestParams requestParams) {
        return null;
    }

    @Override
    public Iterable<FranchiseVanityToken> findAll(Iterable<String> ids, RequestParams requestParams) {
        // TODO Auto-generated method stub
        return null;
    }
}
