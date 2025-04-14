package ir.piana.boot.spuerapp.common.errors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;


//@JsonAutoDetect(getterVisibility= JsonAutoDetect.Visibility.NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class APIResponseDto<T> {
    private  Boolean success;
    private  String errorMessage;
    private  T data;

    /*public APIResponseDto() {
    }*/

    /*public APIResponseDto(Boolean success, String errorMessage) {
        this.success = success;
        this.errorMessage = errorMessage;
    }*/

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public APIResponseDto(@JsonProperty("success") boolean success,
                          @JsonProperty("data") T data) {
        this.success = success;
        if (success) {
            this.data = data;
            errorMessage = null;
        } else {
            this.errorMessage = (String) data;
            this.data = null;
        }
    }

    /*@JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public APIResponseDto(@JsonProperty("success") boolean success,
                          @JsonProperty("errorMessage") String errorMessage,
                          @JsonProperty("data") T data) {
        this.success = success;
        this.errorMessage = errorMessage;
        this.data = data;
    }*/

    public Boolean getSuccess() {
        return success;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public T getData() {
        return data;
    }
}
