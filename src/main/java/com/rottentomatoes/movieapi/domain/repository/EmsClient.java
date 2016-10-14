package com.rottentomatoes.movieapi.domain.repository;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

public class EmsClient<T> {

    private static String AUTH_HEADER = "rt-backend:NVmf)LFdXYc8V8TqvtfHxbqm>36gvYza";

    private EmsConfig emsConfig;

    public EmsClient(EmsConfig context) {
        this.emsConfig = context;
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

    protected InputStream constructInputStream(URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setDoOutput(true);
        connection.setRequestProperty("Authorization", AUTH_HEADER);
        return connection.getInputStream();
    }
    
    public T callEmsList(Map<String, Object> selectParams, String pathBase, String id, CollectionType collectionType) {
        return callEmsCommon(selectParams, pathBase, id, (new JsonDecoder() {
            public T doDecode(URL url) throws JsonParseException, JsonMappingException, IOException {
                return (T) objectMapper.readValue(constructInputStream(url), collectionType);
            }
        })
                );
    }
    
    public T callEmsEntity(Map<String, Object> selectParams, String pathBase, String id, Class c) {
        return callEmsCommon(selectParams, pathBase, id, (new JsonDecoder() {
            public T doDecode(URL url) throws JsonParseException, JsonMappingException, IOException {
                return (T) objectMapper.readValue(constructInputStream(url), c);
            }
        })
                );
    }
    
    public T callEmsCommon(Map<String, Object> selectParams, String pathBase, String id, JsonDecoder jsonDecoder) {
        T jsonResult = null;
        id = (id == null)? "":id;
        try {
            String urlString = emsConfig.getHostUrl() + "/" + pathBase + "/" + id + "/v1";
            MultiValueMap<String,String> params = new LinkedMultiValueMap();
            for (String p: selectParams.keySet()) {
                if (selectParams.get(p) == null) {
                    continue;
                }
                params.add(p, selectParams.get(p).toString().replaceAll("%", "%25"));
            }
            urlString = UriComponentsBuilder.fromUriString(urlString).queryParams(params).build(true).toString();
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
}
