package com.rottentomatoes.movieapi.domain.repository;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

public abstract class EmsClient<T> {

    protected EmsConfig emsConfig;
    protected String hostUrl;

    public EmsClient(EmsConfig config, String hostUrl) {
        this.emsConfig = config;
        this.hostUrl = hostUrl;
    }
    
    protected class JsonDecoder {
        ObjectMapper objectMapper;
        
        public JsonDecoder() {
            objectMapper = new ObjectMapper();
        }

        public T doDecode(URL url) throws JsonParseException, JsonMappingException, IOException {
            return null;
        }
    }

    public T callEmsEntity(Map<String, Object> selectParams, String pathBase, String id, Class c) {
        return callEmsCommon(selectParams, pathBase, id, constructJsonEntityDecoder(c));
    }

    public T callEmsList(Map<String, Object> selectParams, String pathBase, String id, CollectionType collectionType) {
        return callEmsCommon(selectParams, pathBase, id, constructJsonListDecoder(collectionType));
    }

    protected T callEmsCommon(Map<String, Object> selectParams, String pathBase, String id, JsonDecoder jsonDecoder) {
        T jsonResult = null;
        id = (id == null) ? "" : id;
        try {
            MultiValueMap<String,String> params = new LinkedMultiValueMap();
            for (String p: selectParams.keySet()) {
                if (selectParams.get(p) == null) {
                    continue;
                }
                params.add(p, selectParams.get(p).toString().replaceAll("%", "%25").replaceAll(" ", "%20"));
            }
            String urlString = UriComponentsBuilder.fromUriString(constructUrl(pathBase, id)).queryParams(params).build(true).toString();
            URL url = new URL(urlString);
            jsonResult = jsonDecoder.doDecode(url);
        } catch (MalformedURLException mue) {
            emsConfig.log("Malformed URL: ", mue);
        } catch (JsonMappingException | JsonParseException jme) {
            emsConfig.log("Malformed URL: ", jme);
        } catch (IOException ioe) {
            emsConfig.log("Malformed URL: ", ioe);
        }
        return jsonResult;
    }

    protected abstract String constructUrl(String pathBase, String id);

    protected abstract JsonDecoder constructJsonEntityDecoder(Class c);

    protected abstract JsonDecoder constructJsonListDecoder(CollectionType collectionType);
}
