package com.rottentomatoes.movieapi.domain.model.ems;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.type.CollectionType;
import com.rottentomatoes.movieapi.domain.model.AbstractModel;
import com.rottentomatoes.movieapi.domain.model.Affiliate;

/**
 * This is a super class for ems structures, before they are transformed into CF models
 * @author mark
 *
 */
public class EmsModel<T,M> extends AbstractModel {

    public T convert(M m) {
        return null;
    }
    
    public List<T> convertCollection(List<EmsModel> collection) {
        List<T> cfList = new ArrayList<>();
        for(EmsModel m: collection) {
            T a = (T) m.convert(m);
            cfList.add(a);
        }
        return cfList;
    }
}
