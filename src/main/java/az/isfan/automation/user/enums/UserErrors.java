package az.isfan.automation.user.enums;

public enum UserErrors {
    UNKNOWN(1000),
    EMAIL_EMPTY(1001),
    EMAIL_VALID(1002),
    NICK_NAME_EMPTY(1003),
    PASSWORD_EMPTY(1004);

    public final int code;

    UserErrors(int code) {
        this.code = code;
    }
}
