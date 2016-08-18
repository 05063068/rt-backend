package com.rottentomatoes.movieapi.domain.repository;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.rottentomatoes.movieapi.domain.model.Movie;

public class PreEmsClient<T> {
    
    private PreEmsConfig preEmsConfig;
    
    public PreEmsClient(PreEmsConfig context) {
        this.preEmsConfig = context;   
    }
    
    class JsonDecoder {
        ObjectMapper objectMapper;
        
        public JsonDecoder() {
            objectMapper = new ObjectMapper();
        }
        
        public T doDecode(URL url) throws JsonParseException, JsonMappingException, IOException {
            return null;
        }
        
    }
    
    public T callPreEmsList(Map<String, Object> selectParams, String pathBase, String id, CollectionType collectionType) {
        return callPreEmsCommon(selectParams, pathBase, id, (new JsonDecoder() {
            public T doDecode(URL url) throws JsonParseException, JsonMappingException, IOException {
                return (T) objectMapper.readValue(url, collectionType);
            }
        })
                );
    }
    
    public T callPreEmsEntity(Map<String, Object> selectParams, String pathBase, String id, Class c) {
        return callPreEmsCommon(selectParams, pathBase, id, (new JsonDecoder() {
            public T doDecode(URL url) throws JsonParseException, JsonMappingException, IOException {
                return (T) objectMapper.readValue(url, c);
            }
        })
                );
    }
    
    public T callPreEmsCommon(Map<String, Object> selectParams, String pathBase, String id, JsonDecoder jsonDecoder) {
        T jsonResult = null;
        id = (id == null)? "":id;
        try {
            String urlString = preEmsConfig.getHostUrl() + "/" + pathBase + "/" + id;
            MultiValueMap<String,String> params = new LinkedMultiValueMap();
            for (String p: selectParams.keySet()) {
                if (selectParams.get(p) == null) {
                    continue;
                }
                params.add(p, selectParams.get(p).toString());
            }
            urlString = UriComponentsBuilder.fromUriString(urlString).queryParams(params).build(true).toString();
            URL url = new URL(urlString);
            Object rawResponse = jsonDecoder.doDecode(url);
            jsonResult = jsonDecoder.doDecode(url);
        } catch (MalformedURLException mue) {
            preEmsConfig.log("Malformed URL: ", mue);
        } catch (JsonMappingException | JsonParseException jme) {
            preEmsConfig.log("Malformed URL: ", jme);
        } catch (IOException ioe) {
            preEmsConfig.log("Malformed URL: ", ioe);
        }
        return jsonResult;

    }
}
