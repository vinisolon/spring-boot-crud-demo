package br.com.vinisolon.application.controllers;

import br.com.vinisolon.application.exceptions.BusinessRuleException;
import br.com.vinisolon.application.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static br.com.vinisolon.application.common.MockConstants.BLANK_USER_REQUEST;
import static br.com.vinisolon.application.common.MockConstants.DEFAULT_SUCCESS_RESPONSE;
import static br.com.vinisolon.application.common.MockConstants.NULL_USER_REQUEST;
import static br.com.vinisolon.application.common.MockConstants.USER_RESPONSE;
import static br.com.vinisolon.application.common.MockConstants.VALID_CREATE_USER_REQUEST;
import static br.com.vinisolon.application.common.MockConstants.VALID_UPDATE_USER_REQUEST;
import static br.com.vinisolon.application.common.MockConstants.formatter;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    private static final String ROOT_PATH = "/users";
    private static final String ID = "/1";

    @Test
    void create_WithValidData_ReturnsCreated() throws Exception {
        when(userService.create(any())).thenReturn(DEFAULT_SUCCESS_RESPONSE);

        mockMvc.perform(
                        post(ROOT_PATH)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(VALID_CREATE_USER_REQUEST))
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$").value(DEFAULT_SUCCESS_RESPONSE));
    }

    @Test
    void create_WithInvalidData_ReturnsBadRequest() throws Exception {
        mockMvc.perform(
                        post(ROOT_PATH)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(NULL_USER_REQUEST))
                )
                .andDo(print())
                .andExpect(status().isBadRequest());

        mockMvc.perform(
                        post(ROOT_PATH)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(BLANK_USER_REQUEST))
                )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void create_WithExistingEmail_ReturnsUnprocessableEntity() throws Exception {
        when(userService.create(any())).thenThrow(BusinessRuleException.class);

        mockMvc.perform(
                        post(ROOT_PATH)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(VALID_CREATE_USER_REQUEST))
                )
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void update_WithValidData_ReturnsOk() throws Exception {
        when(userService.update(any())).thenReturn(DEFAULT_SUCCESS_RESPONSE);

        mockMvc.perform(
                        put(ROOT_PATH)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(VALID_UPDATE_USER_REQUEST))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(DEFAULT_SUCCESS_RESPONSE));
    }

    @Test
    void update_WithInvalidData_ReturnsBadRequest() throws Exception {
        mockMvc.perform(
                        put(ROOT_PATH)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(NULL_USER_REQUEST))
                )
                .andDo(print())
                .andExpect(status().isBadRequest());

        mockMvc.perform(
                        put(ROOT_PATH)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(BLANK_USER_REQUEST))
                )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void update_WithUnexistingId_ReturnsNotFound() throws Exception {
        when(userService.update(any())).thenThrow(EntityNotFoundException.class);

        mockMvc.perform(
                        put(ROOT_PATH)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(VALID_UPDATE_USER_REQUEST))
                )
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void update_WithExistingEmail_ReturnsUnprocessableEntity() throws Exception {
        when(userService.update(any())).thenThrow(BusinessRuleException.class);

        mockMvc.perform(
                        put(ROOT_PATH)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(VALID_UPDATE_USER_REQUEST))
                )
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void delete_WithAnyId_ReturnsNoContent() throws Exception {
        when(userService.delete(any())).thenReturn(DEFAULT_SUCCESS_RESPONSE);

        mockMvc.perform(
                        delete(ROOT_PATH + ID)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(DEFAULT_SUCCESS_RESPONSE));
    }

    @Test
    void get_WithExistingId_ReturnsUserResponse() throws Exception {
        when(userService.get(anyLong())).thenReturn(USER_RESPONSE);

        mockMvc.perform(
                        get(ROOT_PATH + ID)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(USER_RESPONSE.getUsername()))
                .andExpect(jsonPath("$.email").value(USER_RESPONSE.getEmail()))
                .andExpect(jsonPath("$.createdAt").value(formatter.format(USER_RESPONSE.getCreatedAt())))
                .andExpect(jsonPath("$.updatedAt").value(formatter.format(USER_RESPONSE.getUpdatedAt())));
    }

    @Test
    void get_WithUnexistingId_ReturnsNotFound() throws Exception {
        when(userService.get(anyLong())).thenThrow(EntityNotFoundException.class);

        mockMvc.perform(
                        get(ROOT_PATH + ID)
                )
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void getAll_WithExistingData_ReturnsListOfUserResponse() throws Exception {
        when(userService.getAll()).thenReturn(List.of(USER_RESPONSE, USER_RESPONSE));

        mockMvc.perform(
                        get(ROOT_PATH)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].username").value(USER_RESPONSE.getUsername()))
                .andExpect(jsonPath("$[0].email").value(USER_RESPONSE.getEmail()))
                .andExpect(jsonPath("$[0].createdAt").value(formatter.format(USER_RESPONSE.getCreatedAt())))
                .andExpect(jsonPath("$[0].updatedAt").value(formatter.format(USER_RESPONSE.getUpdatedAt())));
    }

    @Test
    void getAll_WithUnexistingData_ReturnsEmptyList() throws Exception {
        when(userService.getAll()).thenReturn(List.of());

        mockMvc.perform(
                        get(ROOT_PATH)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

}