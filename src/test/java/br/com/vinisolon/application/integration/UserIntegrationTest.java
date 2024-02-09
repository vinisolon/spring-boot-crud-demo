package br.com.vinisolon.application.integration;

import br.com.vinisolon.application.responses.MessageResponse;
import br.com.vinisolon.application.responses.UserResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.Arrays;

import static br.com.vinisolon.application.common.MockConstants.DEFAULT_SUCCESS_RESPONSE;
import static br.com.vinisolon.application.common.MockConstants.CREATE_USER_REQUEST_DIFFERENT_EMAIL;
import static br.com.vinisolon.application.common.MockConstants.UPDATE_USER_REQUEST_DIFFERENT_EMAIL;
import static br.com.vinisolon.application.common.MockConstants.UPDATE_USER_REQUEST_DIFFERENT_ID;
import static br.com.vinisolon.application.common.MockConstants.USER_RESPONSE;
import static br.com.vinisolon.application.common.MockConstants.CREATE_USER_REQUEST;
import static br.com.vinisolon.application.common.MockConstants.UPDATE_USER_REQUEST;
import static br.com.vinisolon.application.enums.ExceptionMessagesEnum.USER_ALREADY_EXISTS_WITH_EMAIL;
import static br.com.vinisolon.application.enums.ExceptionMessagesEnum.USER_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ActiveProfiles("integration")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/delete-users.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@Sql(scripts = "/insert-users.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class UserIntegrationTest {

    @Autowired
    TestRestTemplate testRestTemplate;

    private static final String ROOT_PATH = "/users";
    private static final String ID = "/1";
    private static final String INVALID_ID = "/99";

    // TODO: Need to test @Valid request

    @Test
    void create_WithValidData_ReturnsCreated() {
        ResponseEntity<MessageResponse> response = testRestTemplate.postForEntity(ROOT_PATH, CREATE_USER_REQUEST, MessageResponse.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(DEFAULT_SUCCESS_RESPONSE, response.getBody());
    }

    @Test
    void create_WithExistingEmail_ThrowsException() {
        ResponseEntity<ProblemDetail> response = testRestTemplate.postForEntity(ROOT_PATH, CREATE_USER_REQUEST_DIFFERENT_EMAIL, ProblemDetail.class);

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(USER_ALREADY_EXISTS_WITH_EMAIL.getMessage(), response.getBody().getDetail());
    }

    @Test
    void update_WithValidData_ReturnsOk() {
        ResponseEntity<MessageResponse> response = testRestTemplate.exchange(ROOT_PATH, HttpMethod.PUT, new HttpEntity<>(UPDATE_USER_REQUEST), MessageResponse.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(DEFAULT_SUCCESS_RESPONSE, response.getBody());
    }

    @Test
    void update_WithUnexistingId_ThrowsException() {
        ResponseEntity<ProblemDetail> response = testRestTemplate.exchange(ROOT_PATH, HttpMethod.PUT, new HttpEntity<>(UPDATE_USER_REQUEST_DIFFERENT_ID), ProblemDetail.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(USER_NOT_FOUND.getMessage(), response.getBody().getDetail());
    }

    @Test
    void update_WithExistingEmail_ThrowsException() {
        ResponseEntity<ProblemDetail> response = testRestTemplate.exchange(ROOT_PATH, HttpMethod.PUT, new HttpEntity<>(UPDATE_USER_REQUEST_DIFFERENT_EMAIL), ProblemDetail.class);

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(USER_ALREADY_EXISTS_WITH_EMAIL.getMessage(), response.getBody().getDetail());
    }

    @Test
    void get_WithValidId_ReturnsUserResponse() {
        ResponseEntity<UserResponse> response = testRestTemplate.getForEntity(ROOT_PATH + ID, UserResponse.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(USER_RESPONSE.getUsername(), response.getBody().getUsername());
        assertEquals(USER_RESPONSE.getEmail(), response.getBody().getEmail());
    }

    @Test
    void get_WithInvalidId_ThrowsException() {
        ResponseEntity<ProblemDetail> response = testRestTemplate.getForEntity(ROOT_PATH + INVALID_ID, ProblemDetail.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(USER_NOT_FOUND.getMessage(), response.getBody().getDetail());
    }

    @Test
    void getAll_ReturnsListOfUserResponse() {
        ResponseEntity<UserResponse[]> response = testRestTemplate.getForEntity(ROOT_PATH, UserResponse[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertFalse(Arrays.stream(response.getBody()).toList().isEmpty());
        assertEquals(3, Arrays.stream(response.getBody()).toList().size());
    }

    @Test
    void delete_ReturnsNoContent() {
        ResponseEntity<MessageResponse> response = testRestTemplate.exchange(ROOT_PATH + ID, HttpMethod.DELETE, HttpEntity.EMPTY, MessageResponse.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(DEFAULT_SUCCESS_RESPONSE, response.getBody());
    }

}
