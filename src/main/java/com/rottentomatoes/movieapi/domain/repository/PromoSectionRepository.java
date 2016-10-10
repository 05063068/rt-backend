package com.rottentomatoes.movieapi.domain.repository;

import com.fasterxml.jackson.databind.type.TypeFactory;
import com.rottentomatoes.movieapi.domain.model.PromoItem;
import com.rottentomatoes.movieapi.domain.model.PromoSection;

import io.katharsis.queryParams.RequestParams;
import io.katharsis.repository.ResourceRepository;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class PromoSectionRepository extends AbstractRepository implements ResourceRepository<PromoSection, String> {

    @Override
    public PromoSection findOne(String promo_section_id, RequestParams requestParams) {
        Map<String, Object> selectParams = new HashMap<>();
        PreEmsClient preEmsClient = new PreEmsClient(preEmsConfig);
        PromoSection promoSection = (PromoSection)preEmsClient.callPreEmsEntity(selectParams, "promo-item", promo_section_id, PromoSection.class);
        return promoSection;
    }

    @Override
    public Iterable<PromoSection> findAll(RequestParams requestParams) {
        Map<String, Object> selectParams = new HashMap<>();
        PreEmsClient preEmsClient = new PreEmsClient<Iterable<PromoSection>>(preEmsConfig);
        List<PromoSection> promoSections = (List<PromoSection>) preEmsClient.callPreEmsList(selectParams, "promo-item", null, TypeFactory.defaultInstance().constructCollectionType(List.class,  PromoSection.class));
        return promoSections;
    }

    @Override
    public Iterable<PromoSection> findAll(Iterable<String> strings, RequestParams requestParams) {
        return null;
    }

    @Override
    public void delete(String s) {

    }

    @Override
    public <S extends PromoSection> S save(S entity) {
        return null;
    }
}
