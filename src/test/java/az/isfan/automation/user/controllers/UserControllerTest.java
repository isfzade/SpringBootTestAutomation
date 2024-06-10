package az.isfan.automation.user.controllers;

import az.isfan.automation.common.dto.ErrorDto;
import az.isfan.automation.common.dto.ResponseDto;
import az.isfan.automation.user.dto.UserRegisterDto;
import az.isfan.automation.user.dto.UserResponseDto;
import az.isfan.automation.user.enums.UserErrors;
import az.isfan.automation.user.exceptions.UserException;
import az.isfan.automation.user.exceptions.UserExceptions;
import az.isfan.automation.user.services.UserExceptionService;
import az.isfan.automation.user.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserControllerTest {
    private String nickname = "testNickName";
    private String password = "testPassword";
    private String email = "test@isfan.az";

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;
    @Mock
    private UserExceptionService userExceptionService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void validData_returnCorrectResponse() throws UserExceptions {
        var userRegisterDto = new UserRegisterDto(
                nickname,
                password,
                email
        );

        var userResponseDto = new UserResponseDto(
                123,
                userRegisterDto.nickName()
        );
        var responseDto = new ResponseDto<UserResponseDto>(
                    true,
                    null,
                    userResponseDto
        );

        when(userService.register(userRegisterDto)).thenReturn(responseDto);

        var responseEntity = assertDoesNotThrow(
                () -> userController.register(userRegisterDto), "No exception is expected"
        );

        verify(userService, times(1)).register(userRegisterDto);
        assertNotNull(responseEntity, "Response entity should not be null");
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode(), "Status code is incorrect");
    }

    @Test
    public void exceptionHandler_returnsCorrectResponse() {
        UserException exception = new UserException(
                UserErrors.UNKNOWN.code,
                UserErrors.UNKNOWN,
                "Error Message"
        );
        UserExceptions exceptions = new UserExceptions(
                new ArrayList(List.of(exception))
        );
        var errorDto = new ErrorDto(
                exception.getCode(),
                exception.getType().toString(),
                exception.getMessage()
        );
        var errorDtoList = List.of(errorDto);
        var responseDto = new ResponseDto<List<ErrorDto>>(
                false,
                errorDtoList,
                null
        );

        when(userExceptionService.getErrorResponse(exceptions)).thenReturn(responseDto);

        var responseEntity = userController.handleUserException(exceptions);

        verify(userExceptionService, times(1)).getErrorResponse(exceptions);
        assertNotNull(responseEntity, "Response entity should not be null");
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode(), "Status code is incorrect");
    }
}