package com.rottentomatoes.movieapi.domain.ems;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.introspect.AnnotatedField;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.rottentomatoes.movieapi.domain.repository.EmsRouter;

public abstract class EmsClient<T> {

    protected static int EMS_ID_LIST_LIMIT = 50;

    protected EmsRouter emsRouter;
    protected String hostUrl;
    protected String authHeader;

    public EmsClient(EmsRouter config, String hostUrl, String authHeader) {
        this.emsRouter = config;
        this.hostUrl = hostUrl;
        this.authHeader = authHeader;
    }

    protected static final Cache<String, Object> localCache = CacheBuilder.newBuilder()
            .expireAfterWrite(5, TimeUnit.MINUTES)
            .maximumSize(5000)
            .build();
    
    public String getPathBase(String proposedPathBase) {
        return proposedPathBase;
    }
    
    public String getIdString(String prefix, String id, String suffix) {
        return prefix + id + suffix;
    }

    public T callEmsEntity(Map<String, Object> selectParams, String pathBase, String id, Class c) {
        return callEmsCommon(selectParams, pathBase, id, constructJsonEntityDecoder(c));
    }

    public T callEmsList(Map<String, Object> selectParams, String pathBase, String id, CollectionType collectionType) {
        return callEmsCommon(selectParams, pathBase, id, constructJsonListDecoder(collectionType));
    }

    public T callEmsIdList(Map<String, Object> selectParams, String idPathBase, String id, String collectionPathBase, CollectionType collectionType) {
        List<String> idList = (List<String>) callEmsList(selectParams, idPathBase, id, TypeFactory.defaultInstance().constructCollectionType(List.class, String.class));
        T result = null;
        if (idList != null && idList.size() > 0) {
            String ids;
            List tempList;
            for (int page = 0; page < idList.size(); page += EMS_ID_LIST_LIMIT) {
                ids = StringUtils.join(idList.subList(page, Math.min(page + EMS_ID_LIST_LIMIT, idList.size())), ",");
                if (ids != null && !ids.isEmpty()) {
                    tempList = (List) callEmsCommon(selectParams, collectionPathBase, ids, constructJsonListDecoder(collectionType));
                    if (result == null) {
                        result = (T) tempList;
                    } else if (tempList != null) {
                        ((List) result).addAll(tempList);
                    }
                }
            }
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    protected T callEmsCommon(Map<String, Object> selectParams, String pathBase, String id, JsonDecoder jsonDecoder) {
        T jsonResult = null;
        id = (id == null) ? "" : id;
        try {
            MultiValueMap<String,String> params = new LinkedMultiValueMap();
            for (String p: selectParams.keySet()) {
                if (selectParams.get(p) == null) {
                    continue;
                }
                params.add(p, selectParams.get(p).toString().replaceAll("%", "%25").replaceAll(" ", "%20").replaceAll("&", "%26"));
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

    // Used to get the Http response from either the ems server or the local cache
    protected String getEmsResponse(URL url) throws IOException {
        String response = (String) localCache.getIfPresent(url.toString());
        if (response == null) {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            if (authHeader != null) {
                connection.setRequestProperty("Authorization", authHeader);
            }
            Scanner scanner = new Scanner(connection.getInputStream(), StandardCharsets.UTF_8.name());
            scanner.useDelimiter("\\A");
            if (scanner.hasNext()) {
                response = scanner.next();
                localCache.put(url.toString(), response);
            } else {
                return "";
            }
        }
        return response;
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
            String name = translateName(modelField.getDeclaringClass().getSimpleName(), jsonFieldName);
            return (name != null ? name : super.nameForField(config, modelField, jsonFieldName));
        }

        @Override
        public String nameForGetterMethod(MapperConfig config, AnnotatedMethod method, String defaultName) {
            String name = translateName(method.getDeclaringClass().getSimpleName(), defaultName);
            return (name != null ? name : super.nameForGetterMethod(config, method, defaultName));
        }

        @Override
        public String nameForSetterMethod(MapperConfig config, AnnotatedMethod method, String defaultName) {
            String name = translateName(method.getDeclaringClass().getSimpleName(), defaultName);
            return (name != null ? name : super.nameForSetterMethod(config, method, defaultName));
        }


        protected String translateName(String modelName, String fieldName) {
            return translateName(DEFAULT_EMS_NAMING_CONFIG, modelName, fieldName);
        }

        protected String translateName(String configFile, String modelName, String fieldName) {
            return null;
        }
    }

}
