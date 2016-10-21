package com.rottentomatoes.movieapi.domain.repository;

import com.fasterxml.jackson.databind.type.TypeFactory;
import com.rottentomatoes.movieapi.domain.model.PromoItem;
import com.rottentomatoes.movieapi.domain.model.PromoSection;

import io.katharsis.queryParams.RequestParams;
import io.katharsis.repository.RelationshipRepository;

import org.springframework.stereotype.Component;

import java.util.*;

import static com.rottentomatoes.movieapi.utils.RepositoryUtils.getCountry;

@Component
public class PromoSectionToPromoItemRepository extends AbstractRepository implements RelationshipRepository<PromoSection, String, PromoItem, String> {

    @Override
    public void setRelation(PromoSection source, String targetId, String fieldName) {

    }

    @Override
    public void setRelations(PromoSection source, Iterable<String> targetIds, String fieldName) {

    }

    @Override
    public void addRelations(PromoSection source, Iterable<String> targetIds, String fieldName) {

    }

    @Override
    public void removeRelations(PromoSection source, Iterable<String> targetIds, String fieldName) {

    }

    @Override
    public PromoItem findOneTarget(String sourceId, String fieldName, RequestParams requestParams) {
        return null;
    }

    @Override
    public Iterable<PromoItem> findManyTargets(String promoSectionId, String fieldName, RequestParams requestParams) {
        Map<String, Object> selectParams = new HashMap<>();
        selectParams.put("country", getCountry(requestParams).getCountryCode());

        PreEmsClient preEmsClient = new PreEmsClient<Iterable<PromoItem>>(preEmsConfig);
        List<PromoItem> promoItems = (List<PromoItem>) preEmsClient.callPreEmsList(selectParams, "promo-item", promoSectionId + "/item/", TypeFactory.defaultInstance().constructCollectionType(List.class,  PromoItem.class));


        HashMap<Integer, PromoItem> promoItemMap = getPromoItemsByStartTime(promoItems);

        return promoItemMap.values();
    }

    // Rolls through results and returns one record per sequence number. This is meant to be
    // temporary so that work on the front end can continue. The best solution would be to
    // do this in sql.
    private HashMap<Integer, PromoItem> getPromoItemsByStartTime(List<PromoItem> promoItems) {
        HashMap<Integer, PromoItem> promoItemMap = new HashMap<>();
        for (PromoItem promoItem : promoItems) {
            int sequence = promoItem.getSequence();
            if (promoItemMap.containsKey(sequence)) {
                PromoItem existingPromoItem = promoItemMap.get(sequence);

                if (existingPromoItem.getStartTime().isBefore(promoItem.getStartTime())) {
                    promoItemMap.put(sequence, promoItem);
                }

            } else {
                promoItemMap.put(sequence, promoItem);
            }
        }
        return promoItemMap;
    }
}
