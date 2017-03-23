package com.rottentomatoes.movieapi.domain.converters;

public interface AbstractConverter<T> {

    // converts one or more response objects into the desired output object
    T convert();
}
