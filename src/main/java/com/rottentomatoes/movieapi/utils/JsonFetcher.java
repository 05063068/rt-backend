package com.rottentomatoes.movieapi.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URLConnection;


/**
 * return null when 404 encountered.
 */
public class JsonFetcher {
    private static final ObjectMapper mapper = new ObjectMapper();
    public JsonNode fetch(URI url, String encodingType) {
        try {
            URLConnection urlConnection = url.toURL().openConnection();
            urlConnection.setRequestProperty("Accept", "*/*");
            urlConnection.setConnectTimeout(1 * 1000);
            urlConnection.setReadTimeout(5 * 1000);

            BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), encodingType));
            StringBuilder utf8String = new StringBuilder();
            String line;
            while(null != (line = in.readLine())) {
                byte[] lineutf8 = line.getBytes("UTF-8");
                utf8String.append(new String(lineutf8, "UTF-8"));
            }
            return mapper.readTree(utf8String.toString());
        } catch (FileNotFoundException e) {
            return null;
        } catch (IOException e) {
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public JsonNode fetch(URI url) {
        return fetch(url, "UTF-8");
    }
}