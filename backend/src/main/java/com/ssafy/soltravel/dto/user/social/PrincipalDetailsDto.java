package com.ssafy.soltravel.dto.user.social;

import com.ssafy.soltravel.domain.User;
import com.ssafy.soltravel.handler.OAuth2SuccessHandler;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.*;

public class PrincipalDetailsDto implements UserDetails, OAuth2User {

    private User user;
    private Map<String, Object> attributes;

    //임시
    public PrincipalDetailsDto() {
        user = User.createUser("user1", "password1!", "user1@email.com", "phone1", "address1");
    }

    //일반 로그인 생성자
    public PrincipalDetailsDto(User user) {
        this.user = user;
    }

    //OAuth 로그인 생성자
    public PrincipalDetailsDto(User user, Map<String, Object> attributes) {
        this.user = user;
        this.attributes = attributes;
    }

    /*
    * OAuth2User 인터페이스 메서드
    */
    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    /*
    * UserDetails 인터페이스 메서드
    * 해당 User의 권한 return
    * SecutiryFilterChain에서 권한을 체크할 때 사용됨
    */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return String.valueOf(user.getRole());
            }
        });
        return authorities;
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return user.getName();
    }

    @Override
    public boolean isAccountNonExpired() {
        //return UserDetails.super.isAccountNonExpired();
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        //return UserDetails.super.isAccountNonLocked();
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        //return UserDetails.super.isCredentialsNonExpired();
        return true;
    }

    @Override
    public boolean isEnabled() {
        //return UserDetails.super.isEnabled();
        return true;
    }

    @Override
    public String getName() {
        return "";
    }
}
