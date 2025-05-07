package ir.piana.boot.spuerapp.auth.redis;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OtpSessionEntry {
    private String otp;
    private String forMobile;
}
