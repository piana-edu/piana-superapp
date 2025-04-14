package ir.piana.boot.spuerapp.common.errors;

import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class APIRuntimeException extends RuntimeException {
    private final APIErrorDto apiErrorDto;
    private final List<OneHttpHeader> headers;

    public APIRuntimeException(APIErrorDto apiErrorDto) {
        this(apiErrorDto, new ArrayList<OneHttpHeader>());
    }

    public APIRuntimeException(APIErrorDto apiErrorDto, OneHttpHeader... headers) {
        this(apiErrorDto, Arrays.asList(headers));
    }

    public APIRuntimeException(APIErrorDto apiErrorDto, List<OneHttpHeader> headers) {
        this.apiErrorDto = apiErrorDto;
        this.headers = headers;
    }

    public ResponseEntity<APIResponseDto<?>> getResponseEntity() {
        ResponseEntity.BodyBuilder builder = ResponseEntity.status(apiErrorDto.httpStatus());
        for (OneHttpHeader header : headers) {
            builder.header(header.name(), header.value());
        }
        builder.header("content-type", "application/json");
        return builder.body(apiErrorDto.toResponseDto());
    }
}
