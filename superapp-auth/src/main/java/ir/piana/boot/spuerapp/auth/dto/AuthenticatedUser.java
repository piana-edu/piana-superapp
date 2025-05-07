package ir.piana.boot.spuerapp.auth.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import ir.piana.boot.spuerapp.auth.data.entity.MobileAsUserEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

public class AuthenticatedUser implements Authentication, Serializable {
    @JsonProperty
    private List<PianaGrantedAuthority> authorities;
    @JsonProperty
    private MobileAsUserEntity authenticatedUser;

    public AuthenticatedUser() {
    }

    public AuthenticatedUser(
            List<PianaGrantedAuthority> authorities,
            MobileAsUserEntity authenticatedUser) {
        this.authorities = authorities;
        this.authenticatedUser = authenticatedUser;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @JsonIgnore
    @Override
    public Object getCredentials() {
        return null;
    }

    @JsonIgnore
    @Override
    public Object getDetails() {
        return null;
    }

    @JsonIgnore
    @Override
    public Object getPrincipal() {
        return authenticatedUser;
    }

    @Override
    public boolean isAuthenticated() {
        return authenticatedUser != null;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
    }

    @JsonIgnore
    @Override
    public String getName() {
        return authenticatedUser.getMobile();
    }
}
