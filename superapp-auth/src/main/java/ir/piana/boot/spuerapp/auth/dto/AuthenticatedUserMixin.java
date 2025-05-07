package ir.piana.boot.spuerapp.auth.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import ir.piana.boot.spuerapp.auth.data.entity.MobileAsUserEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@JsonSerialize
public abstract class AuthenticatedUserMixin implements Authentication, Serializable {

    @JsonCreator
    public AuthenticatedUserMixin(
            @JsonProperty List<GrantedAuthority> authorities,
            @JsonProperty String name,
            @JsonProperty MobileAsUserEntity principal,
            @JsonProperty boolean authenticated
            ) {
    }
}
