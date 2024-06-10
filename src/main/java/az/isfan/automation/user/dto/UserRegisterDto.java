package az.isfan.automation.user.dto;

import az.isfan.automation.user.utils.UserErrorMessages;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record UserRegisterDto(
        @JsonProperty("nick-name")
        @NotEmpty(message = UserErrorMessages.NICK_NAME_EMPTY)
        String nickName,

        @NotEmpty(message = UserErrorMessages.PASSWORD_EMPTY)
        String password,

        @NotEmpty(message = UserErrorMessages.EMAIL_EMPTY)
        @Email(message = UserErrorMessages.EMAIL_VALID)
        String email
) {
}
