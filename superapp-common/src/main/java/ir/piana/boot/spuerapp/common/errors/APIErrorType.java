package ir.piana.boot.spuerapp.common.errors;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;

public enum APIErrorType {
    SignInRateLimitError(HttpStatus.TOO_MANY_REQUESTS, "too many times trying to request otp"),
    SignInHaveNotAnyActiveOTP(HttpStatus.BAD_REQUEST, "there is not any active otp for sign-in"),
    SignInProvidedCaptchaIsIncorrect(HttpStatus.BAD_REQUEST, "provided captcha is incorrect"),
    SignInProvidedOTPIsIncorrect(HttpStatus.BAD_REQUEST, "provided otp is incorrect"),
    ;

    private APIErrorDto errorDto;
    private OneHttpHeader[] headers;

    APIErrorType(HttpStatus status, String message, OneHttpHeader... headers) {
        this.errorDto = new APIErrorDto(status, message);
        this.headers = headers;
    }

    public APIRuntimeException getException() {
        return new APIRuntimeException(this.errorDto, this.headers);
    }

    public APIRuntimeException getException(OneHttpHeader... headers) {
        OneHttpHeader[] oneHttpHeaders = ArrayUtils.addAll(headers, this.headers);
        List<OneHttpHeader> list = Arrays.asList(oneHttpHeaders);
        return new APIRuntimeException(this.errorDto, list);
    }

    public void doThrows() throws APIRuntimeException {
        throw new APIRuntimeException(this.errorDto, this.headers);
    }

    public void doThrows(OneHttpHeader... headers) throws APIRuntimeException {
        OneHttpHeader[] oneHttpHeaders = ArrayUtils.addAll(headers, this.headers);
        List<OneHttpHeader> list = Arrays.asList(oneHttpHeaders);
        throw new APIRuntimeException(this.errorDto, list);
    }
}
