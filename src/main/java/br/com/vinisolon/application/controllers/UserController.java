package br.com.vinisolon.application.controllers;

import br.com.vinisolon.application.requests.CreateUserRequest;
import br.com.vinisolon.application.requests.UpdateUserRequest;
import br.com.vinisolon.application.responses.SuccessResponse;
import br.com.vinisolon.application.responses.UserResponse;
import br.com.vinisolon.application.services.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<SuccessResponse> create(@Valid @RequestBody CreateUserRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.create(request));
    }

    @PutMapping
    public ResponseEntity<SuccessResponse> update(@Valid @RequestBody UpdateUserRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.update(request));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<SuccessResponse> delete(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(userService.delete(id));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserResponse> get(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.get(id));
    }

    @GetMapping()
    public ResponseEntity<List<UserResponse>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAll());
    }

}
