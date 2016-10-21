package com.rottentomatoes.movieapi.utils;

import org.apache.http.client.utils.URIBuilder;
import java.net.URI;


public abstract class BaseUrlBuilder<T extends BaseUrlBuilder> {
    protected String host;
    protected String path;
    protected String scheme;

    public String getPath() {
        return path;
    }
    public String getHost() {
        return host;
    }
    public String getScheme() {
        return scheme;
    }

    public T path(String path) {
        this.path = path;
        return (T)this;
    }

    public T host(String host) {
        this.host = host;
        return (T)this;
    }

    // Fluent Builder Setters
    public T scheme(String scheme) {
        this.scheme = scheme;
        return (T)this;
    }

    public abstract URI build();

    protected URIBuilder getBaseBuild() {
        if (path == null) {
            throw new RuntimeException("Catalog API request path must be provided");
        }
        URIBuilder uriBuilder = new URIBuilder();
        uriBuilder.setScheme(scheme);
        uriBuilder.setHost(host);
        uriBuilder.setPath(path);
        return uriBuilder;
    }
}