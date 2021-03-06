package com.rottentomatoes.movieapi.domain.converters.review;

import com.rottentomatoes.movieapi.domain.converters.AbstractConverter;
import com.rottentomatoes.movieapi.domain.model.AudienceReview;
import com.rottentomatoes.movieapi.domain.responses.urating.MovieUserRatingResponse;

import java.util.ArrayList;
import java.util.List;

public class AudienceReviewListConverter implements AbstractConverter<List<AudienceReview>> {

    private List<MovieUserRatingResponse> responseList;

    public AudienceReviewListConverter(List<MovieUserRatingResponse> responseList) {
        this.responseList = responseList;
    }

    public List<AudienceReview> convert() {
        List<AudienceReview> convertedList = new ArrayList();
        for (MovieUserRatingResponse response : responseList) {
            convertedList.add(new AudienceReviewConverter(response).convert());
        }
        return convertedList;
    }
}
