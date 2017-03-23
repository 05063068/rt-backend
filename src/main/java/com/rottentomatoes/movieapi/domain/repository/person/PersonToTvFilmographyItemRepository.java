package com.rottentomatoes.movieapi.domain.repository.person;

import com.fasterxml.jackson.databind.type.TypeFactory;
import com.rottentomatoes.movieapi.domain.clients.ems.EmsClient;
import com.rottentomatoes.movieapi.domain.model.Person;
import com.rottentomatoes.movieapi.domain.model.TvFilmographyItem;
import com.rottentomatoes.movieapi.domain.model.TvSeries;
import com.rottentomatoes.movieapi.domain.repository.AbstractRepository;
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

@SuppressWarnings("rawtypes")
@Component
public class PersonToTvFilmographyItemRepository extends AbstractRepository implements RelationshipRepository<Person, String, TvFilmographyItem, String> {

    @Override
    public void setRelation(Person person, String s, String s2) {

    }

    @Override
    public void setRelations(Person person, Iterable<String> iterable, String s) {

    }

    @Override
    public void addRelations(Person person, Iterable<String> iterable, String s) {

    }

    @Override
    public void removeRelations(Person person, Iterable<String> iterable, String s) {

    }

    @Override
    public TvFilmographyItem findOneTarget(String personId, String fieldName, RequestParams requestParams) {
        return null;
    }

    @Override
    public Iterable<TvFilmographyItem> findManyTargets(String personId, String fieldName, RequestParams requestParams) {
        Map<String, Object> selectParams = new HashMap<>();

        EmsClient emsClient = emsRouter.fetchEmsClientForEndpoint(this.getClass());
        List<TvFilmographyItem> filmography = (List<TvFilmographyItem>)emsClient.callEmsList(selectParams, "person", personId + "/tv-filmography", TypeFactory.defaultInstance().constructCollectionType(List.class,  TvFilmographyItem.class));

        if (filmography != null && filmography.size() > 0) {
            List<String> seriesIdList = filmography.stream()
                    .map(elt -> elt.getId())
                    .collect(Collectors.toList());
            String seriesIds = StringUtils.join(seriesIdList, ",");
            List<TvSeries> seriesList = (List<TvSeries>) emsClient.callEmsList(selectParams, "tv/series", seriesIds, TypeFactory.defaultInstance().constructCollectionType(List.class, TvSeries.class));

            Collections.sort(seriesList,
                    Comparator.comparing(item -> filmography.indexOf(Integer.parseInt(((TvSeries)item).getId()))));
            for (int i = 0; i < seriesList.size() && i < filmography.size(); i++) {
                filmography.get(i).setTvSeries(seriesList.get(i));
            }
        }
        // TODO: metadata for filmography
        return filmography;

    }
}
