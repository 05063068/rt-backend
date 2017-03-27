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
            user.setEmail(response.getEmail());
            user.setFirstName(response.getFirstName());
            user.setLastName(response.getLastName());
            user.setGender(response.getGender());
            user.setCountry(response.getCountry());
            user.setDateOfBirth(response.getDateOfBirth());

            user.setVipId(response.getVipId());
            user.setUserKey(response.getUserKey());
            user.setSourceType(response.getSourceType());
            user.setStatus(response.getStatus());
            user.setCreateDate(response.getCreateDate());
            user.setLastLoginDate(response.getLastLoginDate());

            user.setAccessToken(new AccessTokenConverter(response.getAccessToken()).convert());
        }
        return user;
    }
}
