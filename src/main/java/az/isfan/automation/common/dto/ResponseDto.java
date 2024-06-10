package az.isfan.automation.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ResponseDto<T>(
        @JsonProperty("is-successful")
        Boolean isSuccessful,
        T errors,
        T response
) {
}
