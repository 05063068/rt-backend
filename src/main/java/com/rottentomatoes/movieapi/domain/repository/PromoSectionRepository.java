package com.rottentomatoes.movieapi.domain.repository;

import com.rottentomatoes.movieapi.domain.model.PromoItem;
import com.rottentomatoes.movieapi.domain.model.PromoSection;
import io.katharsis.queryParams.RequestParams;
import io.katharsis.repository.ResourceRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PromoSectionRepository extends AbstractRepository implements ResourceRepository<PromoSection, String> {

    @Override
    public PromoSection findOne(String promo_section_id, RequestParams requestParams) {
        PromoSection promoSection = sqlSession.selectOne("com.rottentomatoes.movieapi.mappers.PromoItemMapper.selectPromoSectionById", promo_section_id);
        return promoSection;
    }

    @Override
    public Iterable<PromoSection> findAll(RequestParams requestParams) {
        List<PromoSection> promoSections= sqlSession.selectList("com.rottentomatoes.movieapi.mappers.PromoItemMapper.selectPromoSections");
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
