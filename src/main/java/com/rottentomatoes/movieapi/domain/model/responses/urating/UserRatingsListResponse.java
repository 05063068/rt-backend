/**
 * Flixster Inc. Copyright (c) 2017. All Rights Reserved.
 */

package com.rottentomatoes.movieapi.domain.model.responses.urating;

import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.rottentomatoes.movieapi.utils.JsonUtilities;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents a response from the User Ratings review list endpoint
 * 
 * @author harry
 */
@Getter
@Setter
public class UserRatingsListResponse {
    private List<UserRating> userRatings;

    public UserRatingsListResponse(final String json) {
        this.userRatings = JsonUtilities.deserialize(json,
                new TypeReference<List<UserRating>>() {});
    }
}
