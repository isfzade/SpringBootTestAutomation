package az.isfan.automation.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UserResponseDto(
        Integer id,
        @JsonProperty("nick-name")
        String nickName
) {
}
