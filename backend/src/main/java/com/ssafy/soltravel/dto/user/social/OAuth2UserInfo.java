package com.ssafy.soltravel.dto.user.social;

public interface OAuth2UserInfo {
    String getProvider();
    String getProviderId();
    String getName();
    String getEmail();
}
