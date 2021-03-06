/**
 * Flixster Inc. Copyright (c) 2017. All Rights Reserved.
 */

package com.rottentomatoes.movieapi.domain.clients;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import org.springframework.http.HttpMethod;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.rottentomatoes.movieapi.domain.requests.AbstractJsonRequest;

/**
 * Represents a general client class for our API interactions while consuming external web services
 *
 * @author harry
 */
public class Client {

    private static final String RESPONSE_DELIMITER = "\\A";
    private static final Cache<String, Object> localCache = CacheBuilder.newBuilder()
            .expireAfterWrite(5, TimeUnit.MINUTES).maximumSize(5000).build();

    public static String makeApiCall(final AbstractJsonRequest request) {
        // Prepare URL string
        final String url = buildUriString(request.getAbsoluteUrl(), request.getQueryParameters());

        // Check for cached value
        String response = (String) localCache.getIfPresent(url);
        if (response == null || request.getHttpMethod() != HttpMethod.GET) {
            response = callApi(url, request.getHttpMethod().name(), request.getHttpHeaders(), request.serialize());
        }


        // Return response
        return response;
    }

    private static String callApi(final String url, final String httpMethod, final Map<String, String> httpHeaders, final String payload) {
        String responseString = "";
        OutputStream outputStream = null;
        Scanner scanner = null;
        try {
            // Prepare HTTP request
            final HttpURLConnection connection = (HttpURLConnection) (new URL(url)).openConnection();
            connection.setRequestMethod(httpMethod);
            connection.setDoOutput(true);
            setHttpHeaders(connection, httpHeaders);

            // Construct payload to send as part of HTTP request
            if (payload != null && !payload.trim().equals("")) {
                byte[] payloadBytes = payload.getBytes(StandardCharsets.UTF_8.name());
                connection.setRequestProperty("Content-length", payloadBytes.length + "");
                OutputStream os = connection.getOutputStream();
                os.write(payloadBytes);
            }

            // Call HTTP request and get response
            scanner = new Scanner(connection.getInputStream(), StandardCharsets.UTF_8.name());
            scanner.useDelimiter(RESPONSE_DELIMITER);
            if (scanner.hasNext()) {
                responseString = scanner.next();
                localCache.put(url, responseString);
            }
        } catch (MalformedURLException mue) {
            mue.printStackTrace();
        } catch (IOException ioe) {
            // TODO Auto-generated catch block
            ioe.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException ioe) {
                    // TODO Auto-generated catch block
                    ioe.printStackTrace();
                }
            }
            if (scanner != null) {
                scanner.close();
            }
        }

        return responseString;
    }

    private static String buildUriString(String absoluteUrl, Map<String, String> queryParams) {
        return UriComponentsBuilder.fromUriString(absoluteUrl)
                .queryParams(prepareQueryParams(queryParams)).build().toString();
    }

    private static MultiValueMap<String, String> prepareQueryParams(
            Map<String, String> queryParams) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        if (queryParams != null) {
            for (String key : queryParams.keySet()) {
                String value = queryParams.get(key);
                if (value != null) {
                    params.add(key, value.replaceAll("%", "%25").replaceAll(" ", "%20")
                            .replaceAll("&", "%26"));
                }
            }
        }
        return params;
    }

    private static void setHttpHeaders(HttpURLConnection connection,
            Map<String, String> httpHeaders) {
        if (httpHeaders != null) {
            for (String key : httpHeaders.keySet()) {
                String value = httpHeaders.get(key);
                if (value != null) {
                    connection.setRequestProperty(key, value);
                }
            }
        }
    }
}
