package com.rottentomatoes.movieapi.domain.repository;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.introspect.AnnotatedField;
import com.fasterxml.jackson.databind.type.CollectionType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

public abstract class EmsClient<T> {

    protected EmsRouter emsRouter;
    protected String hostUrl;

    public EmsClient(EmsRouter config, String hostUrl) {
        this.emsRouter = config;
        this.hostUrl = hostUrl;
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
            emsRouter.log("Malformed URL: ", mue);
        } catch (JsonMappingException | JsonParseException jme) {
            emsRouter.log("Malformed URL: ", jme);
        } catch (IOException ioe) {
            emsRouter.log("Malformed URL: ", ioe);
        }
        return jsonResult;
    }

    protected abstract String constructUrl(String pathBase, String id);

    protected abstract JsonDecoder constructJsonEntityDecoder(Class c);

    protected abstract JsonDecoder constructJsonListDecoder(CollectionType collectionType);

    protected abstract PropertyNamingStrategy getNamingStrategy();

    protected class JsonDecoder {
        ObjectMapper objectMapper;

        public JsonDecoder() {
            objectMapper = new ObjectMapper();
            objectMapper.setPropertyNamingStrategy(getNamingStrategy());
        }

        public T doDecode(URL url) throws JsonParseException, JsonMappingException, IOException {
            return null;
        }
    }

    protected class DefaultEmsNamingStrategy extends PropertyNamingStrategy {

        private static final String DEFAULT_EMS_NAMING_CONFIG = "";

        @Override
        public String nameForField(MapperConfig config, AnnotatedField modelField, String jsonFieldName) {
            String name = translateName(DEFAULT_EMS_NAMING_CONFIG, modelField, jsonFieldName);
            return (name != null ? name : super.nameForField(config, modelField, jsonFieldName));
        }

        protected String translateName(AnnotatedField modelField, String jsonFieldName) {
            return translateName(DEFAULT_EMS_NAMING_CONFIG, modelField, jsonFieldName);
        }

        protected String translateName(String configFile, AnnotatedField modelField, String jsonFieldName) {
            return null;
        }
    }

}
