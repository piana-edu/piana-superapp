package ir.piana.boot.spuerapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kavenegar.sdk.excepctions.ApiException;
import com.kavenegar.sdk.excepctions.HttpException;
import ir.piana.boot.spuerapp.common.errors.APIErrorType;
import ir.piana.boot.spuerapp.common.errors.APIResponseDto;
import ir.piana.boot.spuerapp.data.entity.MobileAsUserEntity;
import ir.piana.boot.spuerapp.data.service.UserManagementService;
import ir.piana.boot.spuerapp.redis.OtpSessionEntry;
import ir.piana.boot.spuerapp.redis.RedisEntry;
import ir.piana.boot.spuerapp.service.RateLimitService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")
public class AuthController {
    //    private final RedisTemplate<String, String> redisTemplate;
    private final RedisEntry<OtpSessionEntry> redisEntry;
    private final RateLimitService rateLimitService;
    private final ObjectMapper objectMapper;
    private final UserManagementService userManagementService;

    public AuthController(
            @Qualifier("signInOtpRedisEntry") RedisEntry<OtpSessionEntry> redisEntry,
            RateLimitService rateLimitService,
            ObjectMapper objectMapper,
            UserManagementService userManagementService) {
        this.redisEntry = redisEntry;
        this.rateLimitService = rateLimitService;
        this.objectMapper = objectMapper;
        this.userManagementService = userManagementService;
    }

    @PostMapping(value = "request-otp", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<APIResponseDto<String>> requestOtp(
            @RequestBody SignInRequestDto signInRequestDto, HttpSession session) {
        if (signInRequestDto == null) {
            return ResponseEntity.badRequest().build();
        }

        String captcha = (String) session.getAttribute("captcha-for-signin");
        if (signInRequestDto.captcha == null || !signInRequestDto.captcha.equalsIgnoreCase(captcha)) {
            APIErrorType.SignInProvidedCaptchaIsIncorrect.doThrows();
        }

        rateLimitService.checkSignInLimit(signInRequestDto.mobile);

        try {
            //ToDo should be generate random code
            String otp = "1234";
            if (!redisEntry.get(session.getId()).isEmpty()) {
                return ResponseEntity.badRequest().build();
            }
            redisEntry.put(session.getId(), new OtpSessionEntry(otp, signInRequestDto.mobile));

//            redisTemplate.restore(session.getId() + "signin.otp", otp.getBytes(), 120, TimeUnit.SECONDS, true);
//            KavenegarApi api = new KavenegarApi("6B6773663258696B304F65576F4433516739573856513D3D");

            // ToDo should be send otp
            /*SendResult Result = api.verifyLookup(
                    userMobile.mobile,
                    otp,
                    "lineup-verify");*/
        } catch (HttpException ex) { // در صورتی که خروجی وب سرویس 200 نباشد این خطارخ می دهد.
            System.out.print("HttpException  : " + ex.getMessage());
        } catch (ApiException ex) { // در صورتی که خروجی وب سرویس 200 نباشد این خطارخ می دهد.
            System.out.print("ApiException : " + ex.getMessage());
        }

        return ResponseEntity.ok(new APIResponseDto(true, "sended!"));
    }

    @PostMapping("verify-otp")
    public ResponseEntity<VerifyOTPResponseDto> verifyOtp(@RequestBody VerifyOTPRequestDto verifyOTPRequestDto, HttpSession session) {
        if (verifyOTPRequestDto == null) {
            return ResponseEntity.badRequest().build();
        }

        try {
            OtpSessionEntry otpSessionEntry = redisEntry.getAndDelete(session.getId())
                    .orElseThrow(APIErrorType.SignInHaveNotAnyActiveOTP::getException);

            if (!otpSessionEntry.getOtp().equalsIgnoreCase(verifyOTPRequestDto.otp)) {
                APIErrorType.SignInProvidedOTPIsIncorrect.doThrows();
            }

            MobileAsUserEntity mobileAsUserEntity = userManagementService.findOrRegister(otpSessionEntry.getForMobile());
            return ResponseEntity.ok(new VerifyOTPResponseDto(mobileAsUserEntity.getMobile()));
        } catch (HttpException ex) { // در صورتی که خروجی وب سرویس 200 نباشد این خطارخ می دهد.
            System.out.print("HttpException  : " + ex.getMessage());
        } catch (ApiException ex) { // در صورتی که خروجی وب سرویس 200 نباشد این خطارخ می دهد.
            System.out.print("ApiException : " + ex.getMessage());
        }

        return ResponseEntity.ok().build();
    }

    record SignInRequestDto(String mobile, String captcha) {
    }

    record VerifyOTPRequestDto(String otp) {
    }

    record VerifyOTPResponseDto(String mobile) {
    }
}
