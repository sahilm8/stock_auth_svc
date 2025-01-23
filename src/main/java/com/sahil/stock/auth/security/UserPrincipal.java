package com.sahil.stock.auth.security;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.sahil.stock.auth.model.AuthUser;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(staticName = "from")
public class UserPrincipal implements UserDetails {
    private final AuthUser authUser;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return authUser.getPassword();
    }

    @Override
    public String getUsername() {
        return authUser.getEmail();
    }
}
