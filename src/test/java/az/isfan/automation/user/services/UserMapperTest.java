package az.isfan.automation.user.services;

import az.isfan.automation.user.dto.UserRegisterDto;
import az.isfan.automation.user.dto.UserResponseDto;
import az.isfan.automation.user.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {
    private UserMapper userMapper;
    private String nickname = "testNickName";
    private String password = "testPassword";
    private String email = "test@isfan.az";

    @BeforeEach
    void setUp(){
        userMapper = new UserMapper();
    }

    @Test
    public void toUser_convertCorrectly() {
        var userDto = new UserRegisterDto(
                nickname,
                password,
                password
        );
        var user = userMapper.toUser(userDto);
        assertNotNull(user, "User cannot be null");
        assertNotNull(user.getNickName(), "Nickname is null");
        assertNotNull(user.getEmail(), "Email is null");
        assertNotNull(user.getPassword(), "Password is null");
        assertNull(user.getId(), "Id should be null");
        assertEquals(userDto.nickName(), user.getNickName(), "Nickname is incorrect");
        assertEquals(userDto.password(), user.getPassword(), "Password is incorrect");
        assertEquals(userDto.email(), user.getEmail(), "Email is incorrect");
    }

    @Test
    public void toUserResponseDto_convertCorrectly() {
        var user = User.builder()
                .nickName(nickname)
                .password(password)
                .email(email)
                .build();
        var userResponseDto = userMapper.toUserResponseDto(user);
        assertNotNull(userResponseDto.nickName(), "Nickname is null");
        assertEquals(user.getNickName(), userResponseDto.nickName(), "Nickname is incorrect");
        assertNull(userResponseDto.id(), "Id should be null");
    }

    @Test
    public void toResponseDto_convertCorrectly() {
        var userResponseDto = new UserResponseDto(
                123,
                nickname
        );
        var responseDto = userMapper.toResponseDto(userResponseDto);
        assertNotNull(responseDto.isSuccessful(), "isSuccessful is null");
        assertNotNull(responseDto.response(), "response is null");
        assertNull(responseDto.errors(), "errors should be null");
        assertEquals(true, responseDto.isSuccessful(), "isSuccessful");
        assertEquals(userResponseDto.nickName(), responseDto.response().nickName(), "nickName is incorrect");
    }
}