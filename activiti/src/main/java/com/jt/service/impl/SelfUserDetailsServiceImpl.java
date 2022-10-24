package com.jt.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author fjl
 */
@Slf4j
@Component
public class SelfUserDetailsServiceImpl implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return findOneUserByName(username);
    }

    public User findOneUserByName(String username) {
        List<GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList("");
        // 密码置空
        return new User(username, "", authorities);
    }
}

