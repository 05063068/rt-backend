package com.rottentomatoes.movieapi.domain.ems;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.rottentomatoes.movieapi.domain.model.clients.Client;
import com.rottentomatoes.movieapi.domain.model.requests.AbstractJsonRequest;
import com.rottentomatoes.movieapi.domain.repository.EmsRouter;

import java.io.IOException;
import java.util.HashMap;

/**
 * Client that handles requests/responses for TV-EMS.
 * 
 * Deprecated - use {@link Client} and {@link AbstractJsonRequest} to make HTTP requests
 */
@Deprecated
@SuppressWarnings({ "unchecked", "rawtypes", "serial" })
public class TvEmsClient<T> extends EmsClient<T> {

    public TvEmsClient(EmsRouter config, String hostUrl, String authHeader) {
        super(config, hostUrl, authHeader);
    }

    @Override
    protected String constructUrl(String pathBase, String id) {
        return this.hostUrl + "/" + pathBase + "/" + id + "/v1";
    }

    @Override
    protected JsonDecoder constructJsonEntityDecoder(Class c) {
        return new JsonDecoder() {
            public T doDecode(String response) throws JsonParseException, JsonMappingException, IOException {
                return (T) objectMapper.readValue(response, c);
            }
        };
    }

    @Override
    protected JsonDecoder constructJsonListDecoder(CollectionType collectionType) {
        return new JsonDecoder() {
            public T doDecode(String response) throws JsonParseException, JsonMappingException, IOException {
                return (T) objectMapper.readValue(response, collectionType);
            }
        };
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
            namingMap.put("Critic.tmApproved", "approved");
            namingMap.put("Franchise.vanity", "vanityUrl");
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
