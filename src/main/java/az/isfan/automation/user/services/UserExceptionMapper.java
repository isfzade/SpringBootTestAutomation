package az.isfan.automation.user.services;

import az.isfan.automation.common.dto.ErrorDto;
import az.isfan.automation.user.enums.UserErrors;
import az.isfan.automation.user.exceptions.UserException;
import az.isfan.automation.user.exceptions.UserExceptions;
import az.isfan.automation.user.utils.UserErrorMessages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserExceptionMapper {
    private Logger logger = LoggerFactory.getLogger(UserExceptionMapper.class);

    public List<ErrorDto> toErrorDtoList(UserExceptions exceptions) {
        logger.info("toErrorDtoList: ");

        var errorDtoList = new ArrayList<ErrorDto>();
        for (UserException exception : exceptions.getExceptions()) {
            errorDtoList.add(
                    toErrorDto(exception)
            );
        }
        return errorDtoList;
    }

    public UserException toUserException(String message) {
        logger.info("toUserException: message = {}", message);

        var userErrorEnum = getCorrespondingUserErrorEnumToMessage(message);
        return new UserException(
                userErrorEnum.code,
                userErrorEnum,
                message
        );
    }

    private ErrorDto toErrorDto(UserException exception) {
        logger.info("toErrorDto: exception = {}", exception);

        return new ErrorDto(
                exception.getCode(),
                exception.getType().toString(),
                exception.getMessage()
        );
    }

    private UserErrors getCorrespondingUserErrorEnumToMessage(String message) {
        return switch (message) {
            case UserErrorMessages.EMAIL_EMPTY -> UserErrors.EMAIL_EMPTY;
            case UserErrorMessages.EMAIL_VALID -> UserErrors.EMAIL_VALID;
            case UserErrorMessages.NICK_NAME_EMPTY -> UserErrors.NICK_NAME_EMPTY;
            case UserErrorMessages.PASSWORD_EMPTY -> UserErrors.PASSWORD_EMPTY;
            default -> UserErrors.UNKNOWN;
        };
    }
}
