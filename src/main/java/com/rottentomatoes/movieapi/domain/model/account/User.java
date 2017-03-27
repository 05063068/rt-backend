package com.rottentomatoes.movieapi.domain.model.account;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.rottentomatoes.movieapi.domain.model.AbstractModel;
import io.katharsis.resource.annotations.JsonApiResource;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@JsonApiResource(type = "user")
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class User extends AbstractModel {

    protected String vipId;
    protected String userKey;
    protected String sourceType;
    protected String status;
    protected String createDate;
    protected String lastLoginDate;

    protected String mainImage;
    protected String email;
    protected String firstName;
    protected String lastName;
    protected String gender;
    protected String country;
    protected Date dateOfBirth;

    protected AccessToken accessToken;
}