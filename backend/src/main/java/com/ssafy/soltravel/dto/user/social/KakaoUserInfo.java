package com.ssafy.soltravel.dto.user.social;

import java.util.LinkedHashMap;
import java.util.Map;

public class KakaoUserInfo implements OAuth2UserInfo {
    public KakaoUserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    private Map<String, Object> attributes;

    @Override
    public String getProvider() {
        return "kakao";
    }

    @Override
    public String getProviderId() {
        return String.valueOf(attributes.get("id"));
    }

    @Override
    public String getName() {
        return String.valueOf(attributes.get("name"));
    }

    @Override
    public String getEmail() {
        LinkedHashMap accountMap = (LinkedHashMap)attributes.get("kakao_account");
        return String.valueOf(accountMap.get("email"));
    }
}
