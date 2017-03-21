package com.rottentomatoes.movieapi.domain.model.account;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.rottentomatoes.movieapi.domain.model.AbstractModel;
import io.katharsis.resource.annotations.JsonApiResource;
import lombok.Getter;
import lombok.Setter;

@JsonApiResource(type = "session")
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Session extends AbstractModel {

    protected AccessToken accessToken;

    protected RefreshToken refreshToken;

    protected Long expiresIn;

}