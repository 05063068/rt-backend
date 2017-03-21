package com.rottentomatoes.movieapi.domain.requests.commonidentity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rottentomatoes.movieapi.domain.requests.AbstractJsonRequest;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public abstract class AbstractCommonIdentityRequest extends AbstractJsonRequest {

    private static final String BASE_URL_PROPERTY = "datasource.common-identity.url";

    public AbstractCommonIdentityRequest(final Environment environment, final HttpMethod httpMethod, final String urlService,
                                         final String urlPath, @Nullable final Map<String, String> queryParameters,
                                         @Nullable final Map<String, String> httpHeaders, @Nullable Object payloadObject,
                                         @Nullable final String apiStatName) {
        super(httpMethod, prepareAbsoluteUrl(environment, urlService, urlPath), queryParameters,
                prepareHttpHeaders(httpHeaders), apiStatName);
        try {
            this.setRequestPayload((new ObjectMapper()).writeValueAsString(payloadObject));
        } catch (JsonProcessingException e) {
            // TODO throw error for failing to construct payload
        }
    }

    private static Map<String, String> prepareHttpHeaders(final Map<String,String> additionalHeaders) {
        Map<String,String> httpHeaders = new HashMap<>();
        httpHeaders.put("Content-Type", "application/json");
        httpHeaders.put("App-Id", "RT");
        if (additionalHeaders != null) {
            httpHeaders.putAll(additionalHeaders);
        }
        return httpHeaders;
    }

    private static String prepareAbsoluteUrl(final Environment environment, final String service, final String path) {
        return String.format(Locale.US, environment.getProperty(BASE_URL_PROPERTY) + "/" + path, service, service);
    }
}
