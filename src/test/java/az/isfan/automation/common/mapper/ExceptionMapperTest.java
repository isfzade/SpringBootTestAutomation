package az.isfan.automation.common.mapper;


import az.isfan.automation.common.dto.ErrorDto;
import az.isfan.automation.common.dto.ResponseDto;
import az.isfan.automation.user.enums.UserErrors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ExceptionMapperTest {
        private ExceptionMapper exceptionMapper;

        private List<ErrorDto> errorDtoList = new ArrayList<ErrorDto>();
        private ErrorDto dtoError = new ErrorDto(
                UserErrors.UNKNOWN.code,
                UserErrors.UNKNOWN.toString(),
                "any message"
        );

    @BeforeEach
    void setUp(){
        errorDtoList.clear();
        exceptionMapper = new ExceptionMapper();
    }

    @Test
    public void oneErrorToResponseDto_convertsCorrectly() {
        errorDtoList.add(dtoError);
        ResponseDto<List<ErrorDto>> response = exceptionMapper.toResponseDto(errorDtoList);
        assertNotNull(response, "Response should not be null");
        assertFalse(response.isSuccessful(), "isSuccessful is incorrect");
        assertNull(response.response(), "response is incorrect");
        assertNotNull(response.errors(), "errors should not be null");
        assertEquals(response.errors().size(), 1, "Error size is incorrect");
        assertEquals(dtoError.type(), response.errors().get(0).type(), "Error type is incorrect");
    }

    @Test
    public void twoErrorToResponseDto_convertsCorrectly() {
        errorDtoList.add(dtoError);
        errorDtoList.add(dtoError);
        ResponseDto<List<ErrorDto>> response = exceptionMapper.toResponseDto(errorDtoList);
        assertNotNull(response, "Response should not be null");
        assertFalse(response.isSuccessful(), "isSuccessful is incorrect");
        assertNull(response.response(), "response is incorrect");
        assertNotNull(response.errors(), "errors should not be null");
        assertEquals(response.errors().size(), 2, "Error size is incorrect");
        for (ErrorDto error: response.errors()) {
            assertEquals(dtoError.type(), error.type(), "Incorrect type");
        }
    }
}