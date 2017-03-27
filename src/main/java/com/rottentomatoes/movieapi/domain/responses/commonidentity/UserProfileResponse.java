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
    protected String rtId;
    @JsonProperty("vip_id")
    protected String vipId;
    protected String userKey;
    protected String accessToken;
    protected String sourceType;
    protected String status;

    protected String mainImage;
    protected String email;
    protected String firstName;
    protected String lastName;
    protected String gender;
    protected String country;
    @JsonProperty("dob")
    protected Date dateOfBirth;

    protected String createDate;
    protected String lastLoginDate;

    protected String ipAddress;
    protected String hashAlgorithm;
    protected String passwordSalt;

}
