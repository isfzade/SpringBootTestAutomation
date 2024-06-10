package az.isfan.automation.user.services;

import az.isfan.automation.common.dto.ResponseDto;
import az.isfan.automation.user.dto.UserRegisterDto;
import az.isfan.automation.user.dto.UserResponseDto;
import az.isfan.automation.user.enums.UserErrors;
import az.isfan.automation.user.exceptions.UserException;
import az.isfan.automation.user.exceptions.UserExceptions;
import az.isfan.automation.user.models.User;
import az.isfan.automation.user.repos.UserRepo;
import az.isfan.automation.user.validators.UserRestValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {
    private String nickname = "testNickName";
    private String password = "testPassword";
    private String email = "test@isfan.az";

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRestValidator userRestValidator;
    @Mock
    private UserRepo userRepo;
    @Mock
    private UserMapper userMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void registerWithValidFields_correctMethodsCalled() throws UserExceptions {
        var userRegisterDto = new UserRegisterDto(
                nickname,
                password,
                email
        );
        var user = User.builder().build();
        var userResponseDto = new UserResponseDto(0, nickname);
        ResponseDto<UserResponseDto> response = new ResponseDto(
                true,
                null,
                userResponseDto
        );

        when(userMapper.toUser(userRegisterDto)).thenReturn(user);
        when(userRepo.save(user)).thenReturn(user);
        when(userMapper.toUserResponseDto(user)).thenReturn(userResponseDto);
        when(userMapper.toResponseDto(userResponseDto)).thenReturn(response);

        var returnedResponse = assertDoesNotThrow(
                () -> userService.register(userRegisterDto), "Register should not throw exception"
        );

        verify(userRestValidator, times(1)).validate(userRegisterDto);
        verify(userMapper, times(1)).toUser(userRegisterDto);
        verify(userRepo, times(1)).save(user);
        verify(userMapper, times(1)).toUserResponseDto(user);
        verify(userMapper, times(1)).toResponseDto(userResponseDto);
        assertNotNull(returnedResponse, "Response should not be null");
        assertEquals(response.response(), returnedResponse.response(), "Returned response is not correct");
    }

    @Test
    public void exceptionThrown_rightMethodsCalled() throws UserExceptions {
        var userRegisterDto = new UserRegisterDto(
                null,
                password,
                email
        );
        var exception = new UserException(
                UserErrors.UNKNOWN.code,
                UserErrors.UNKNOWN,
                "Error Message"
        );
        var exceptions = new UserExceptions(
                new ArrayList(List.of(exception))
        );
        var user = User.builder().build();
        var userResponseDto = new UserResponseDto(0, nickname);

        doThrow(exceptions).when(userRestValidator).validate(userRegisterDto);

        assertThrows(
                UserExceptions.class,
                () -> userService.register(userRegisterDto), "Register should throw exception"
        );

        verify(userRestValidator, times(1)).validate(userRegisterDto);
        verify(userMapper, never()).toUser(userRegisterDto);
        verify(userRepo, never()).save(user);
        verify(userMapper, never()).toUserResponseDto(user);
        verify(userMapper, never()).toResponseDto(userResponseDto);
    }
}