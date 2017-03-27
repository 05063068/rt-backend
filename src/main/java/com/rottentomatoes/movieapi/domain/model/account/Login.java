package com.rottentomatoes.movieapi.domain.model.account;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.rottentomatoes.movieapi.domain.model.AbstractModel;
import io.katharsis.resource.annotations.JsonApiResource;
import lombok.Getter;
import lombok.Setter;

@JsonApiResource(type = "login")
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Login extends AbstractModel {

    protected IdentityToken identityToken;

    protected Session session;

    protected User user;

}