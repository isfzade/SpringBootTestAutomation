package az.isfan.automation.user.validators;

import az.isfan.automation.user.dto.UserRegisterDto;
import az.isfan.automation.user.exceptions.UserExceptions;
import az.isfan.automation.user.services.UserExceptionMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class UserRestValidatorTest {
    private String nickname = "testNickName";
    private String password = "testPassword";
    private String email = "test@isfan.az";

    @InjectMocks
    private UserRestValidator userRestValidator;

    @Mock
    private UserExceptionMapper userExceptionMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void correctVariables_noExceptionThrown() {
        var userRegisterDto = new UserRegisterDto(
                nickname,
                password,
                email
        );

        assertDoesNotThrow(
                () -> userRestValidator.validate(userRegisterDto), "No exception was expected"
        );
    }

    @Test
    public void emailIsNull_OneExceptionThrown() {
        var userRegisterDto = new UserRegisterDto(
                nickname,
                password,
                null
        );

        var exceptions = assertThrows(
                UserExceptions.class,
                () -> userRestValidator.validate(userRegisterDto), "Exception was expected"
        );
        assertEquals(1, exceptions.getExceptions().size(), "Exception size do not match");
    }

    @Test
    public void nickNameIsNull_OneExceptionThrown() {
        var userRegisterDto = new UserRegisterDto(
                null,
                password,
                email
        );

        var exceptions = assertThrows(
                UserExceptions.class,
                () -> userRestValidator.validate(userRegisterDto), "Exception was expected"
        );
        assertEquals(1, exceptions.getExceptions().size(), "Exception size do not match");
    }

    @Test
    public void passwordNameIsNull_OneExceptionThrown() {
        var userRegisterDto = new UserRegisterDto(
                nickname,
                null,
                email
        );

        var exceptions = assertThrows(
                UserExceptions.class,
                () -> userRestValidator.validate(userRegisterDto), "Exception was expected"
        );
        assertEquals(1, exceptions.getExceptions().size(), "Exception size do not match");
    }

    @Test
    public void emailIsNotValid_OneExceptionThrown() {
        var userRegisterDto = new UserRegisterDto(
                nickname,
                password,
                "incorrectEmail"
        );

        var exceptions = assertThrows(
                UserExceptions.class,
                () -> userRestValidator.validate(userRegisterDto), "Exception was expected"
        );
        assertEquals(1, exceptions.getExceptions().size(), "Exception size do not match");
    }

    @Test
    public void allVariablesNull_ThreeExceptionThrown() {
        var userRegisterDto = new UserRegisterDto(
                null,
                null,
                null
        );

        var exceptions = assertThrows(
                UserExceptions.class,
                () -> userRestValidator.validate(userRegisterDto), "Exception was expected"
        );
        assertEquals(3, exceptions.getExceptions().size(), "Exception size do not match");
    }
}