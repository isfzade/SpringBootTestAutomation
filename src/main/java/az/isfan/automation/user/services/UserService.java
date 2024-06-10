package az.isfan.automation.user.services;

import az.isfan.automation.common.dto.ResponseDto;
import az.isfan.automation.user.dto.UserRegisterDto;
import az.isfan.automation.user.dto.UserResponseDto;
import az.isfan.automation.user.exceptions.UserExceptions;
import az.isfan.automation.user.repos.UserRepo;
import az.isfan.automation.user.validators.UserRestValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    public UserService(UserRestValidator userRestValidator, UserRepo userRepo, UserMapper userMapper) {
        this.userRestValidator = userRestValidator;
        this.userRepo = userRepo;
        this.userMapper = userMapper;
    }

    private final UserRestValidator userRestValidator;
    private final UserRepo userRepo;
    private final UserMapper userMapper;


    public ResponseDto<UserResponseDto> register(
            UserRegisterDto userRegisterDto
    ) throws UserExceptions {
        logger.info("register: ");

        userRestValidator.validate(userRegisterDto);
        var user = userMapper.toUser(userRegisterDto);
        var userFromDb = userRepo.save(user);
        var userResponseDto = userMapper.toUserResponseDto(userFromDb);
        return userMapper.toResponseDto(userResponseDto);
    }
}
