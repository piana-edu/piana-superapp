package ir.piana.boot.spuerapp.auth.errors;

import ir.piana.boot.spuerapp.common.errors.APIResponseDto;
import ir.piana.boot.spuerapp.common.errors.APIRuntimeException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = APIRuntimeException.class, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<APIResponseDto<?>> handleConflict(
            APIRuntimeException ex, WebRequest request) {
        ResponseEntity<APIResponseDto<?>> responseEntity = ex.getResponseEntity();
        return responseEntity;
    }
}
