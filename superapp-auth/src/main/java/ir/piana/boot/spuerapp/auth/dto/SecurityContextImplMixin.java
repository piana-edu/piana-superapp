package ir.piana.boot.spuerapp.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SecurityContextImplMixin {
    public SecurityContextImplMixin(
            @JsonProperty AuthenticatedUser authentication
    ) {}
}
