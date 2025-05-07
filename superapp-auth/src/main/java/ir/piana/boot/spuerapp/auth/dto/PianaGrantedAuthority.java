package ir.piana.boot.spuerapp.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.security.core.GrantedAuthority;

public class PianaGrantedAuthority implements GrantedAuthority {

    @JsonProperty
    private String authority;

    public PianaGrantedAuthority() {
    }

    public PianaGrantedAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
