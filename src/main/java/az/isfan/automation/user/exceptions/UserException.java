package az.isfan.automation.user.exceptions;

import az.isfan.automation.user.enums.UserErrors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class UserException extends Exception {
    private int code;
    private UserErrors type;
    private String message;

    @Override
    public String toString() {
        return "UserException(code: " + code +
                ", type: " + type +
                ", message: " + message + ")";
    }
}
