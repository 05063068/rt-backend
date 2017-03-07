/**
 * Flixster Inc. Copyright (c) 2017. All Rights Reserved.
 */

package com.rottentomatoes.movieapi.domain.model.responses.ems;

import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.rottentomatoes.movieapi.utils.JsonUtilities;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents a response from the EMS Box Office endpoint
 * 
 * @author harry
 */
@Getter
@Setter
public class EmsBoxOfficeResponse {
    private List<EmsMovie> emsMovies;

    public EmsBoxOfficeResponse(final String json) {
        this.emsMovies = JsonUtilities.deserialize(json, new TypeReference<List<EmsMovie>>() {});
    }
}
