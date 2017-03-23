package com.rottentomatoes.movieapi.domain.converters.movie;

import com.rottentomatoes.movieapi.domain.converters.AbstractConverter;
import com.rottentomatoes.movieapi.domain.model.Movie;
import com.rottentomatoes.movieapi.domain.responses.ems.MovieResponse;

import java.util.ArrayList;
import java.util.List;

public class MovieListConverter implements AbstractConverter<List<Movie>> {

    private List<MovieResponse> responseList;

    public MovieListConverter(List<MovieResponse> responseList) {
        this.responseList = responseList;
    }

    public List<Movie> convert() {
        List<Movie> convertedList = new ArrayList();
        for (MovieResponse response : responseList) {
            convertedList.add(new MovieConverter(response).convert());
        }
        return convertedList;
    }
}
