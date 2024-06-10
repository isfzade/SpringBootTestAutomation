package az.isfan.automation.user.services;

import az.isfan.automation.common.dto.ErrorDto;
import az.isfan.automation.user.enums.UserErrors;
import az.isfan.automation.user.exceptions.UserException;
import az.isfan.automation.user.exceptions.UserExceptions;
import az.isfan.automation.user.utils.UserErrorMessages;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class UserExceptionMapperTest {
    private UserExceptionMapper exceptionMapper;
    private ArrayList<String> messages = new ArrayList<String>();

    @BeforeEach
    void setUp(){
        fillMessages();
        exceptionMapper = new UserExceptionMapper();
    }

    @Test
    public void toUserException_createdCorrectly() {
        for (String message: messages) {
            var exception = exceptionMapper.toUserException(message);
            assertNotNull(exception, "Exception for " + message + " is null");
            assertEquals(message, exception.getMessage(), "Exception message is not correct");
            assertNotNull(exception.getCode(), "Exception code for " + message + " is null");
            assertTrue(exception.getCode()>1000, "Exception code for " + message + " should be bigger than 1000, got = " + exception.getCode());
            assertNotNull(exception.getType(), "Exception type for " + message + " is null");
        }
    }

    @Test
    public void toUserException_receiveUnknownException() {
        var message = "random message";
        var exception = exceptionMapper.toUserException(message);
        assertNotNull(exception, "Exception is null");
        assertEquals(message, exception.getMessage(), "Exception message is not correct");
        assertNotNull(exception.getCode(), "Exception code is null");
        assertNotNull(exception.getType(), "Exception type for is null");
        assertEquals(UserErrors.UNKNOWN.code, exception.getCode(), "Exception code is incorrect");
        assertEquals(UserErrors.UNKNOWN, exception.getType(), "Exception type is incorrect");
    }

    @Test
    public void toErrorDtoList_createdCorrectly() {
        var exceptions = new ArrayList<UserException>();
        for (String message: messages) {
            var exception = exceptionMapper.toUserException(message);
            exceptions.add(exception);
        }
        var myExceptions = UserExceptions.builder().exceptions(exceptions).build();
        assertEquals(myExceptions.getExceptions().size(), exceptions.size(), "Count of exceptions is incorrect");
        var errorDtoList = exceptionMapper.toErrorDtoList(myExceptions);
        assertNotNull(errorDtoList, "Error dto list is null");
        assertEquals(errorDtoList.size(), exceptions.size(), "Count of error dto is incorrect");
        int index = 0;
        for (ErrorDto errorDto: errorDtoList) {
            assertNotNull(errorDto.code(), "Dto code is null");
            assertNotNull(errorDto.type(), "Dto type is null");
            assertNotNull(errorDto.message(), "Dto message is null");
            assertEquals(exceptions.get(index).getCode(), errorDto.code(), "Code is incorrect");
            assertEquals(exceptions.get(index).getType().toString(), errorDto.type(), "Type is incorrect");
            assertEquals(exceptions.get(index).getMessage(), errorDto.message(), "Message is incorrect");
            index++;
        }
    }

    private void fillMessages() {
        this.messages.addAll(
                Arrays.asList(
                        UserErrorMessages.EMAIL_EMPTY,
                        UserErrorMessages.EMAIL_VALID,
                        UserErrorMessages.NICK_NAME_EMPTY,
                        UserErrorMessages.PASSWORD_EMPTY
                )
        );
    }
}