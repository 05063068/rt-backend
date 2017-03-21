package com.rottentomatoes.movieapi.domain.responses.commonidentity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class UserProfileResponse {

    @JsonProperty("rt_id")
    public String rtId;

    public String firstName;

    public String lastName;

    public String mainImage;

    public String gender;

    @JsonProperty("dob")
    public Date dateOfBirth;

    public String accessToken;

}