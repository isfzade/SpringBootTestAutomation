package az.isfan.automation.user.services;

import az.isfan.automation.common.dto.ErrorDto;
import az.isfan.automation.common.mapper.ExceptionMapper;
import az.isfan.automation.user.enums.UserErrors;
import az.isfan.automation.user.exceptions.UserException;
import az.isfan.automation.user.exceptions.UserExceptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

class UserExceptionServiceTest {
    private String nickname = "testNickName";
    private String password = "testPassword";
    private String email = "test@isfan.az";
    private UserException exception = new UserException(
            UserErrors.UNKNOWN.code,
            UserErrors.UNKNOWN,
            "Error Message"
    );
    private UserExceptions exceptions = new UserExceptions(
            new ArrayList(List.of(exception))
    );

    @InjectMocks
    private UserExceptionService userExceptionService;

    @Mock
    private ExceptionMapper exceptionMapper;
    @Mock
    private UserExceptionMapper userExceptionMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getErrorResponse_correctMethodsCalled() {
        var errorDto = new ErrorDto(
                exception.getCode(),
                exception.getType().toString(),
                exception.getMessage()
        );
        var errorDtoList = new ArrayList<ErrorDto>(
                List.of(errorDto)
        );
        when(userExceptionMapper.toErrorDtoList(exceptions)).thenReturn(errorDtoList);

        userExceptionService.getErrorResponse(exceptions);

        verify(userExceptionMapper, times(1)).toErrorDtoList(exceptions);
        verify(exceptionMapper, times(1)).toResponseDto(errorDtoList);
    }
}