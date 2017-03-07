package com.rottentomatoes.movieapi.domain.ems;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.introspect.AnnotatedField;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.rottentomatoes.movieapi.domain.model.clients.Client;
import com.rottentomatoes.movieapi.domain.model.requests.AbstractJsonRequest;
import com.rottentomatoes.movieapi.domain.model.requests.ems.EmsClientRequest;
import com.rottentomatoes.movieapi.domain.repository.EmsRouter;

/**
 * Client that handles requests/responses for services that RTCF calls
 * 
 * Deprecated - use {@link Client} and {@link AbstractJsonRequest} to make HTTP requests
 */
@Deprecated
@SuppressWarnings({ "unchecked", "rawtypes", "serial" })
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

    protected T callEmsCommon(Map<String, Object> selectParams, String pathBase, String id,
            JsonDecoder jsonDecoder) {
        try {
            final String absoluteUrl = constructUrl(pathBase, (id == null) ? "" : id);
            final EmsClientRequest request = new EmsClientRequest(absoluteUrl, selectParams,
                    authHeader);
            final String response = Client.makeApiCall(request);
            return jsonDecoder.doDecode(response);
        } catch (MalformedURLException mue) {
            emsRouter.log("Malformed URL: ", mue);
        } catch (JsonMappingException | JsonParseException jme) {
            emsRouter.log("Malformed URL: ", jme);
        } catch (IOException ioe) {
            emsRouter.log("Malformed URL: ", ioe);
        }
        return null;
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

        public T doDecode(String response) throws JsonParseException, JsonMappingException, IOException {
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
