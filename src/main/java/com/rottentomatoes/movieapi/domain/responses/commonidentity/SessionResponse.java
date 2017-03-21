package com.rottentomatoes.movieapi.domain.responses.commonidentity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class SessionResponse {

    public String accessToken;

    public String refreshToken;

    @JsonProperty("expires_in")
    public Long expiresIn;

}