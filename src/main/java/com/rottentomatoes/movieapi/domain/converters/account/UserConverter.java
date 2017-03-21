package com.rottentomatoes.movieapi.domain.converters.account;

import com.rottentomatoes.movieapi.domain.converters.AbstractConverter;
import com.rottentomatoes.movieapi.domain.model.account.User;
import com.rottentomatoes.movieapi.domain.responses.commonidentity.UserProfileResponse;

public class UserConverter implements AbstractConverter<User> {

    private UserProfileResponse response;

    public UserConverter(UserProfileResponse response) {
        this.response = response;
    }
    public User convert() {
        User user = new User();
        if (response != null) {
            user.setId(response.getRtId());
            user.setMainImage(response.getMainImage());
            user.setFirstName(response.getFirstName());
            user.setLastName(response.getLastName());
            user.setDateOfBirth(response.getDateOfBirth());
            user.setGender(response.getGender());
            user.setAccessToken(new AccessTokenConverter(response.getAccessToken()).convert());
        }
        return user;
    }
}
