package com.ssafy.soltravel.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {

    private SecurityUtil() {
        // 인스턴스화 방지
    }

    public static Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getPrincipal() == null) {
            throw new IllegalStateException("No authentication found in context");
        }

        Long userId = (Long) authentication.getPrincipal();
        // 주의: 사용자 ID를 Long으로 캐스팅할 수 있는지 확인해야 합니다.
        return userId;
    }
}
