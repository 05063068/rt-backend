package com.rottentomatoes.movieapi.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.katharsis.response.HttpStatus;
import io.katharsis.servlet.SampleKatharsisFilter;

/**
 * Filter to Expand the response Json by including the related objects in the core object 
 */
@Component
public class ExpandJsonResponseFilter extends SampleKatharsisFilter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        String expand = req.getParameter("expand");
        if (expand != null && expand.equalsIgnoreCase("true")) {
            CharResponseWrapper wrappedResponse = new CharResponseWrapper((HttpServletResponse) res);
            chain.doFilter(req, wrappedResponse);
            if (HttpStatus.OK_200 == wrappedResponse.getStatus() && wrappedResponse.getContentType().contains("json")) {
                String out = new String(wrappedResponse.getByteArray());
                ObjectMapper mapper = new ObjectMapper();
                JsonNode jsonOut = mapper.readTree(out);
                JsonNode expandedJson = JsonApiExpander.expand(jsonOut);
                res.getOutputStream().write(expandedJson.toString().getBytes());
            }
        } else {
            chain.doFilter(req, res);
        }
    }

}
