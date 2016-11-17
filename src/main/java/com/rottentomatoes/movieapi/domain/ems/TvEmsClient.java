package com.rottentomatoes.movieapi.domain.ems;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.introspect.AnnotatedField;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.rottentomatoes.movieapi.domain.repository.EmsRouter;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class TvEmsClient<T> extends EmsClient<T> {

    private static final String AUTH_HEADER = "rt-backend:NVmf)LFdXYc8V8TqvtfHxbqm>36gvYza";

    public TvEmsClient(EmsRouter config, String hostUrl) {
        super(config, hostUrl);
    }

    @Override
    protected String constructUrl(String pathBase, String id) {
        return this.hostUrl + "/" + pathBase + "/" + id + "/v1";
    }

    @Override
    protected JsonDecoder constructJsonEntityDecoder(Class c) {
        return new JsonDecoder() {
            public T doDecode(URL url) throws JsonParseException, JsonMappingException, IOException {
                return (T) objectMapper.readValue(constructInputStream(url), c);
            }
        };
    }

    @Override
    protected JsonDecoder constructJsonListDecoder(CollectionType collectionType) {
        return new JsonDecoder() {
            public T doDecode(URL url) throws JsonParseException, JsonMappingException, IOException {
                return (T) objectMapper.readValue(constructInputStream(url), collectionType);
            }
        };
    }

    protected InputStream constructInputStream(URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setDoOutput(true);
        connection.setRequestProperty("Authorization", AUTH_HEADER);
        return connection.getInputStream();
    }

    @Override
    protected PropertyNamingStrategy getNamingStrategy() {
        return new TvEmsNamingStrategy();
    }

    protected class TvEmsNamingStrategy extends DefaultEmsNamingStrategy {

        private static final String TV_EMS_NAMING_CONFIG = "";

        @Override
        public String translateName(AnnotatedField modelField, String jsonFieldName)
        {
            String name = translateName(TV_EMS_NAMING_CONFIG, modelField, jsonFieldName);
            return (name != null ? name : super.translateName(modelField, jsonFieldName));
        }
    }
}
