package com.rottentomatoes.movieapi.filter;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.katharsis.invoker.KatharsisInvokerBuilder;
import io.katharsis.resource.registry.ResourceRegistry;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;


/**
 * KatharsisInvoker builder.
 */
public class CustomKatharsisInvokerBuilder extends KatharsisInvokerBuilder {
    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";

    @Override
    protected ObjectMapper createObjectMapper(ResourceRegistry resourceRegistry) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.registerModule(createDataBindingModule(resourceRegistry));
        mapper.registerModule(new JavaTimeModule());
        mapper.setDateFormat(new SimpleDateFormat(DATE_TIME_FORMAT));
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        return mapper;
    }
}
