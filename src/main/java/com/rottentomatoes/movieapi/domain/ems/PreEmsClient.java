package com.rottentomatoes.movieapi.domain.ems;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.introspect.AnnotatedField;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.rottentomatoes.movieapi.domain.repository.EmsRouter;

public class PreEmsClient<T> extends EmsClient<T> {

    public PreEmsClient(EmsRouter config, String hostUrl) {
        super(config, hostUrl, null);
    }

    @Override
    protected String constructUrl(String pathBase, String id) {
        return this.hostUrl + "/" + pathBase + "/" + id;
    }

    @Override
    protected JsonDecoder constructJsonEntityDecoder(Class c) {
        return new JsonDecoder() {
            public T doDecode(URL url) throws JsonParseException, JsonMappingException, IOException {
                return (T) objectMapper.readValue(getEmsResponse(url), c);
            }
        };
    }

    @Override
    protected JsonDecoder constructJsonListDecoder(CollectionType collectionType) {
        return new JsonDecoder() {
            public T doDecode(URL url) throws JsonParseException, JsonMappingException, IOException {
                return (T) objectMapper.readValue(getEmsResponse(url), collectionType);
            }
        };
    }

    @Override
    protected PropertyNamingStrategy getNamingStrategy() {
        return new PreEmsNamingStrategy();
    }

    protected class PreEmsNamingStrategy extends DefaultEmsNamingStrategy {

        private static final String PRE_EMS_NAMING_CONFIG = "";

        @Override
        public String translateName(String modelName, String fieldName)
        {
            String name = translateName(PRE_EMS_NAMING_CONFIG, modelName, fieldName);
            return (name != null ? name : super.translateName(modelName, fieldName));
        }
    }
}
