/**
 * Flixster Inc. Copyright (c) 2017. All Rights Reserved.
 */

package com.rottentomatoes.movieapi.domain.requests;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

import com.fasterxml.jackson.databind.JsonMappingException;
import org.springframework.http.HttpMethod;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents an abstract request class which encapsulates a request to external web services
 * <p>
 * <b>NOTE: </b> All fields must be marked as {@code @JsonIgnore}.
 * Otherwise, they get serialized in the request.
 * </p>
 * @author harry
 */
@Getter
@Setter
public abstract class AbstractJsonRequest {

    private static final String AUTHENTICATION_HEADER_NAME = "Authorization";

    /*
     * Represents the HTTP Verb for this HTTP request
     */
    @JsonIgnore
    protected HttpMethod httpMethod;

    /*
     * Represents the absolute URL of the API endpoint
     */
    @JsonIgnore
    protected String absoluteUrl;

    /*
     * Represents the query parameters
     */
    @JsonIgnore
    protected Map<String, String> queryParameters;

    /*
     * Represents the HTTP Headers for this request
     */
    @JsonIgnore
    protected Map<String, String> httpHeaders;

    /*
     * Represents the payload sent as part of the HTTP request
     */
    @JsonIgnore
    protected String requestPayload;


    /**
     * Represents the stats name for the API to be used for statsD purposes
     */
    @JsonIgnore
    protected String apiStatName = null;

    protected AbstractJsonRequest(HttpMethod httpMethod, String absoluteUrl,
            @Nullable Map<String, String> queryParameters,
            @Nullable Map<String, String> httpHeaders, @Nullable String apiStatName) {

        setHttpMethod(httpMethod);
        setAbsoluteUrl(absoluteUrl);
        setQueryParameters(queryParameters);
        setHttpHeaders(httpHeaders);
        setApiStatName(apiStatName);
    }

    public String serialize() {
        try {
            return (new ObjectMapper()).writeValueAsString(this);
        } catch (JsonMappingException e) {
            // ignore, not all responses require serializers
        } catch (JsonProcessingException e) {
            // TODO throw invalid request exception
            e.printStackTrace();
        }
        return null;
    }

    protected static Map<String, String> prepareAuthenticationHttpHeader(
            final String authorizationHeaderValue) {
        final Map<String, String> authenticationHttpHeader = new HashMap<String, String>(1);
        authenticationHttpHeader.put(AUTHENTICATION_HEADER_NAME, authorizationHeaderValue);
        return authenticationHttpHeader;
    }
}
