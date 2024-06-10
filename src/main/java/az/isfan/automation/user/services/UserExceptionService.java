package az.isfan.automation.user.services;

import az.isfan.automation.common.dto.ErrorDto;
import az.isfan.automation.common.dto.ResponseDto;
import az.isfan.automation.common.mapper.ExceptionMapper;
import az.isfan.automation.user.exceptions.UserExceptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserExceptionService {
    private Logger logger = LoggerFactory.getLogger(UserExceptionService.class);

    @Autowired
    public UserExceptionService(ExceptionMapper exceptionMapper, UserExceptionMapper userExceptionMapper) {
        this.exceptionMapper = exceptionMapper;
        this.userExceptionMapper = userExceptionMapper;
    }

    private final ExceptionMapper exceptionMapper;
    private final UserExceptionMapper userExceptionMapper;

    public ResponseDto<List<ErrorDto>> getErrorResponse(
            UserExceptions exceptions
    ) {
        logger.info("getErrorResponse: ");

        var errorDtoList = userExceptionMapper.toErrorDtoList(exceptions);
        return exceptionMapper.toResponseDto(errorDtoList);
    }
}
