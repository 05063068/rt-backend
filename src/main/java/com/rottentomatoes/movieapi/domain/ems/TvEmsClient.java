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
import java.util.HashMap;

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

        private HashMap<String, String> namingMap;

        public TvEmsNamingStrategy() {
            namingMap = new HashMap<>();

            // TV Personnel
            namingMap.put("MovieCast.characters", "characterName");
            namingMap.put("MovieCast.person", "moviePerson");
            namingMap.put("Person.name", "actorName");
            namingMap.put("Person.mainImage", "preferredImage");
        }

        @Override
        public String translateName(String modelName, String fieldName)
        {
            String key = modelName + "." + fieldName;
            if (namingMap.containsKey(key)) {
                return namingMap.get(key);
            }

            String name = translateName(TV_EMS_NAMING_CONFIG, modelName, fieldName);
            return (name != null ? name : super.translateName(modelName, fieldName));
        }
    }
}
