package az.isfan.automation.user.validators;

import az.isfan.automation.user.dto.UserRegisterDto;
import az.isfan.automation.user.exceptions.UserException;
import az.isfan.automation.user.exceptions.UserExceptions;
import az.isfan.automation.user.services.UserExceptionMapper;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserRestValidator {
    private Logger logger = LoggerFactory.getLogger(UserRestValidator.class);

    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();
    private final UserExceptionMapper userExceptionMapper;

    public UserRestValidator(
            UserExceptionMapper userExceptionMapper
    ) {
        this.userExceptionMapper = userExceptionMapper;
    }

    public void validate(UserRegisterDto userRegisterDto) throws UserExceptions {
        var violations = validator.validate(userRegisterDto);
        if (!violations.isEmpty()) {
            logger.info("validate: violations.size = {}", violations.size());
            List<UserException> exceptions = new ArrayList<UserException>();
            for (ConstraintViolation<UserRegisterDto> violation : violations) {
                var message = violation.getMessage();
                var exception = userExceptionMapper.toUserException(message);
                exceptions.add(exception);
            }
            throw UserExceptions.builder()
                    .exceptions(exceptions)
                    .build();
        }
    }
}
