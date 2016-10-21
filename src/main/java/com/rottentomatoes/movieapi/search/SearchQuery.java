package com.rottentomatoes.movieapi.search;

import com.fasterxml.jackson.databind.JsonNode;

import com.rottentomatoes.movieapi.utils.JsonFetcher;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class SearchQuery {

    private static JsonFetcher jsonFetcher = new JsonFetcher();

    private URIBuilder uriBuilder = new URIBuilder();
    private List<NameValuePair> queryParams = new ArrayList<>();

    /* Constructor */
    public SearchQuery(String type){
        uriBuilder.setScheme("http");
        uriBuilder.setHost("search.aws.prod.flixster.com");
        uriBuilder.setPath("/v1/" + type);
    }

    /* Shortcut - initialize params via HashMap */
    public SearchQuery(String type, Map<String, Object> params){
        this(type);
        for (Map.Entry<String, Object> entry : params.entrySet())
        {
            if(entry.getValue() instanceof List)
            {
                for(Object listItem : (List)entry.getValue()) {
                    uriBuilder.addParameter(entry.getKey(), (String)listItem);
                }
            }
            else{
                uriBuilder.addParameter(entry.getKey(), (String)entry.getValue());
            }

        }
    }

    public JsonNode execute() {
        try {
            URI uri = uriBuilder.build();
            return jsonFetcher.fetch(uri);

        } catch (URISyntaxException e) {
            throw new RuntimeException("Error constructing catalog API request", e);
        }
    }
}
