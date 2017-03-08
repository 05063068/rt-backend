
package com.rottentomatoes.movieapi.utils;

import java.io.IOException;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtilities {
    private static final ObjectMapper JSON_OBJECT_MAPPER = new ObjectMapper();

    public static <T> T deserialize(final String json, final TypeReference<T> typeReference) {
        try {
            return JSON_OBJECT_MAPPER.readValue(json, typeReference);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }
}
