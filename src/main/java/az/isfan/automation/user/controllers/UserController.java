package az.isfan.automation.user.controllers;

import az.isfan.automation.common.dto.ResponseDto;
import az.isfan.automation.user.dto.UserRegisterDto;
import az.isfan.automation.user.dto.UserResponseDto;
import az.isfan.automation.user.exceptions.UserExceptions;
import az.isfan.automation.user.services.UserExceptionService;
import az.isfan.automation.user.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public UserController(UserService userService, UserExceptionService userExceptionService) {
        this.userService = userService;
        this.userExceptionService = userExceptionService;
    }

    private final UserService userService;
    private final UserExceptionService userExceptionService;

    @PostMapping("/register")
    public ResponseEntity<ResponseDto<UserResponseDto>> register(
            @RequestBody UserRegisterDto userRegisterDto
    ) throws UserExceptions {
        logger.info("register: ");

        var responseDto = userService.register(userRegisterDto);
        return new ResponseEntity(
                responseDto,
                HttpStatus.CREATED
        );
    }

    @ExceptionHandler(UserExceptions.class)
    public ResponseEntity<?> handleUserException(
            UserExceptions exceptions
    ) {
        logger.info("handleUserException: ");

        var responseDto = userExceptionService.getErrorResponse(exceptions);
        return new ResponseEntity(
                responseDto,
                HttpStatus.BAD_REQUEST
        );
    }
}
