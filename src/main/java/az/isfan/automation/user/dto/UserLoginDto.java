package az.isfan.automation.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record UserLoginDto(
        @NotEmpty
        @Email
        String password,

        @NotEmpty
        @Email
        String email
) {
}
