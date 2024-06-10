package az.isfan.automation.user.validators;

import az.isfan.automation.user.dto.UserRegisterDto;
import az.isfan.automation.user.utils.UserErrorMessages;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RestValidatorTest {

    private Validator validator;

    private String nickname = "testNickName";
    private String password = "testPassword";
    private String email = "test@isfan.az";

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    @Test
    public void correctVariables_noViolations() {
        var userRegisterDto = new UserRegisterDto(
                nickname,
                password,
                email
        );

        Set<ConstraintViolation<UserRegisterDto>> violations = validator.validate(userRegisterDto);
        assertTrue(violations.isEmpty(), "No violation was expected");
    }

    @Test
    public void allIncorrectVariables_ThreeViolations() {
        var userRegisterDto = new UserRegisterDto(
                null,
                null,
                null
        );

        Set<ConstraintViolation<UserRegisterDto>> violations = validator.validate(userRegisterDto);
        assertEquals(3, violations.size(), "3 violations were expected");
    }

    @Test
    public void nullEmail_correctMessage() {
        var userRegisterDto = new UserRegisterDto(
                nickname,
                password,
                null
        );

        Set<ConstraintViolation<UserRegisterDto>> violations = validator.validate(userRegisterDto);
        assertEquals(1, violations.size(), "1 violations were expected");
        var violation = violations.iterator().next();
        assertEquals(UserErrorMessages.EMAIL_EMPTY, violation.getMessage(), "Message was incorrect");
    }

    @Test
    public void nullNickName_correctMessage() {
        var userRegisterDto = new UserRegisterDto(
                null,
                password,
                email
        );

        Set<ConstraintViolation<UserRegisterDto>> violations = validator.validate(userRegisterDto);
        assertEquals(1, violations.size(), "1 violations were expected");
        var violation = violations.iterator().next();
        assertEquals(UserErrorMessages.NICK_NAME_EMPTY, violation.getMessage(), "Message was incorrect");
    }

    @Test
    public void nullPassword_correctMessage() {
        var userRegisterDto = new UserRegisterDto(
                nickname,
                null,
                email
        );

        Set<ConstraintViolation<UserRegisterDto>> violations = validator.validate(userRegisterDto);
        assertEquals(1, violations.size(), "1 violations were expected");
        var violation = violations.iterator().next();
        assertEquals(UserErrorMessages.PASSWORD_EMPTY, violation.getMessage(), "Message was incorrect");
    }

    @Test
    public void invalidEmail_correctMessage() {
        var userRegisterDto = new UserRegisterDto(
                nickname,
                password,
                "invalidEmail"
        );

        Set<ConstraintViolation<UserRegisterDto>> violations = validator.validate(userRegisterDto);
        assertEquals(1, violations.size(), "1 violations were expected");
        var violation = violations.iterator().next();
        assertEquals(UserErrorMessages.EMAIL_VALID, violation.getMessage(), "Message was incorrect");
    }
}