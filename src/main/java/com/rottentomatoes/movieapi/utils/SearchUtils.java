package com.rottentomatoes.movieapi.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.rottentomatoes.movieapi.search.SearchQuery;
import io.katharsis.queryParams.RequestParams;
import io.katharsis.response.BaseMetaDataInformation;
import io.katharsis.response.MetaInformation;

import java.util.Map;

import static com.rottentomatoes.movieapi.utils.RepositoryUtils.getLimit;
import static com.rottentomatoes.movieapi.utils.RepositoryUtils.getOffset;

/**
 * Utility functions to work with the Search Microservice (http://search.prod.aws.flixster.com/documentation/index.html)
 */
public class SearchUtils {
    public static MetaInformation loadSearchMeta(JsonNode searchResult, RequestParams requestParams){
        BaseMetaDataInformation mdi = new BaseMetaDataInformation();
        mdi.count = searchResult.path("found").intValue();
        mdi.offset = getOffset("", requestParams);
        mdi.limit = getLimit("", requestParams);
        return mdi;
    }

    public static JsonNode callSearchService(String endpoint, RequestParams requestParams) {
        return callSearchService(endpoint, requestParams, "search");
    }


    public static JsonNode callSearchService(String endpoint, RequestParams requestParams, String searchparam) {
        Map<String, Object> searchObj = (Map<String, Object>) requestParams.getFilters().get(searchparam);
        if (requestParams.getPagination() != null && !requestParams.getPagination().isEmpty()) {
            searchObj.put("limit", getLimit("", requestParams));
            searchObj.put("offset", getOffset("", requestParams));
        }
        SearchQuery q = new SearchQuery(endpoint, searchObj);
        JsonNode json = q.execute();

        return json;
    }
}

