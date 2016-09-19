package com.rottentomatoes.movieapi.domain.repository;

import com.rottentomatoes.movieapi.domain.model.PromoItem;
import com.rottentomatoes.movieapi.domain.model.PromoSection;
import io.katharsis.queryParams.RequestParams;
import io.katharsis.repository.RelationshipRepository;
import org.springframework.stereotype.Component;

import java.util.*;

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
        selectParams.put("promo_section", promoSectionId);
        selectParams.put("country", getCountry(requestParams).getCountryCode());

        List<PromoItem> promoItems = sqlSession.selectList("com.rottentomatoes.movieapi.mappers.PromoItemMapper.selectPromoItemsByPromoSectionId", selectParams);


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
