package az.isfan.automation.user.services;

import az.isfan.automation.common.dto.ResponseDto;
import az.isfan.automation.user.dto.UserRegisterDto;
import az.isfan.automation.user.dto.UserResponseDto;
import az.isfan.automation.user.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {
    private Logger logger = LoggerFactory.getLogger(UserMapper.class);

    public User toUser(UserRegisterDto userRegisterDto) {
        logger.info("toUser: userRegisterDto = {}", userRegisterDto);

        var user = User.builder()
                .nickName(userRegisterDto.nickName())
                .email(userRegisterDto.email())
                .password(userRegisterDto.password())
                .build();
        return user;
    }

    public UserResponseDto toUserResponseDto(User user) {
        logger.info("toUserResponseDto: ");

        var userResponseDto = new UserResponseDto(
                user.getId(),
                user.getNickName()
        );
        return userResponseDto;
    }

    public ResponseDto<UserResponseDto> toResponseDto(UserResponseDto userResponseDto) {
        logger.info("toResponseDto: userResponseDto = {}", userResponseDto);

        var responseDto = new ResponseDto(
                true,
                null,
                userResponseDto
        );
        return responseDto;
    }
}
