package com.rottentomatoes.movieapi.filter;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import io.katharsis.invoker.KatharsisInvokerBuilder;
import io.katharsis.resource.registry.ResourceRegistry;


/**
 * KatharsisInvoker builder.
 */
public class CustomKatharsisInvokerBuilder extends KatharsisInvokerBuilder {
    @Override
    protected ObjectMapper createObjectMapper(ResourceRegistry resourceRegistry) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.registerModule(createDataBindingModule(resourceRegistry));
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        return mapper;
    }
}
