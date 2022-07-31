package ru.homework.tasktracker.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.homework.tasktracker.model.dto.UserFullDto;

import java.util.Collection;

public class UserDetailsImpl implements UserDetails {
    private final UserFullDto userFullDto;

    public UserDetailsImpl(UserFullDto userFullDto) {
        this.userFullDto = userFullDto;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return userFullDto.getRole().getPermissions();
    }

    @Override
    public String getPassword() {
        return userFullDto.getPassword();
    }

    @Override
    public String getUsername() {
        return userFullDto.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
