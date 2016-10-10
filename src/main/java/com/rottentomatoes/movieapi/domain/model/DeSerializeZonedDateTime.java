package com.rottentomatoes.movieapi.domain.model;

import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class DeSerializeZonedDateTime extends JsonDeserializer<ZonedDateTime> {

    @Override
    public ZonedDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException,
            JsonProcessingException {
        String dateStr = p.getValueAsString();
        ZonedDateTime zonedDate = null;
        if (dateStr != null) {
            // Strip "[America/Los Angles]" if it is there
            int bracketInd = dateStr.indexOf('[');
            if (bracketInd > 0) {
                dateStr = dateStr.substring(0, bracketInd);
            }
            Date date = ctxt.parseDate(dateStr);
            ZoneId zoneId = ZoneId.of("America/Los_Angeles");
            zonedDate = ZonedDateTime.ofInstant(date.toInstant(),
                    zoneId);
        }
        return zonedDate;
    }
}
