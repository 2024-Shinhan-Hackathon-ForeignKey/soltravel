package com.ssafy.soltravel.service.user.social;

import com.ssafy.soltravel.domain.User;
import com.ssafy.soltravel.dto.user.social.PrincipalDetailsDto;
import com.ssafy.soltravel.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrincipalService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByName(username).orElseThrow(() -> new UsernameNotFoundException(username));
        return new PrincipalDetailsDto(user);
    }
}
