package az.isfan.automation.user.controllers;

import az.isfan.automation.common.dto.ErrorDto;
import az.isfan.automation.common.dto.ResponseDto;
import az.isfan.automation.user.dto.UserRegisterDto;
import az.isfan.automation.user.dto.UserResponseDto;
import az.isfan.automation.user.enums.UserErrors;
import az.isfan.automation.user.exceptions.UserException;
import az.isfan.automation.user.exceptions.UserExceptions;
import az.isfan.automation.user.services.UserExceptionService;
import az.isfan.automation.user.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(controllers = UserController.class)
class UserHttpRequestTest {
    private String nickname = "testNickName";
    private String password = "testPassword";
    private String email = "test@isfan.az";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;
    @MockBean
    private UserExceptionService userExceptionService;

    @Test
    public void postRequest_returnsCorrectValue() throws Exception {
        var userRegisterDto = new UserRegisterDto(
                nickname,
                password,
                email
        );
        var userResponseDto = new UserResponseDto(
                123,
                userRegisterDto.nickName()
        );
        var responseDto = new ResponseDto<UserResponseDto>(
                true,
                null,
                userResponseDto
        );
        when(userService.register(userRegisterDto)).thenReturn(responseDto);

        ResultActions response = mockMvc.perform(
                post("/api/v1/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                objectMapper.writeValueAsString(userRegisterDto)
                        )
        );

        response
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.is-successful").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response").value(Matchers.notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.nick-name").value(nickname))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors").value(Matchers.nullValue()));
    }

    @Test
    public void inValidPostRequest_returnsCorrectValue() throws Exception {
        var userRegisterDto = new UserRegisterDto(
                null,
                password,
                email
        );
        UserException exception = new UserException(
                UserErrors.UNKNOWN.code,
                UserErrors.UNKNOWN,
                "Error Message"
        );
        UserExceptions exceptions = new UserExceptions(
                new ArrayList(List.of(exception))
        );
        var errorDto = new ErrorDto(
                exception.getCode(),
                exception.getType().toString(),
                exception.getMessage()
        );
        var errorDtoList = List.of (errorDto);
        var responseDto = new ResponseDto<List<ErrorDto>>(
                false,
                errorDtoList,
                null
        );

        when(userService.register(userRegisterDto)).thenThrow(exceptions);
        when(userExceptionService.getErrorResponse(exceptions)).thenReturn(responseDto);

        ResultActions response = mockMvc.perform(
                post("/api/v1/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                objectMapper.writeValueAsString(userRegisterDto)
                        )
        );

        response
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        response
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response").value(Matchers.nullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.is-successful").value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors").value(Matchers.notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors[0]").value(Matchers.notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors[0].code").value(exception.getCode()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors[0].type").value(exception.getType().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors[0].message").value(exception.getMessage()));
    }
}