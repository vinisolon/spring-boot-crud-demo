package br.com.vinisolon.application.services;

import br.com.vinisolon.application.entities.User;
import br.com.vinisolon.application.exceptions.BusinessRuleException;
import br.com.vinisolon.application.mappers.UserMapper;
import br.com.vinisolon.application.repositories.UserRepository;
import br.com.vinisolon.application.requests.CreateUserRequest;
import br.com.vinisolon.application.requests.UpdateUserRequest;
import br.com.vinisolon.application.responses.SuccessResponse;
import br.com.vinisolon.application.responses.UserResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

import static br.com.vinisolon.application.enums.Messages.DEFAULT_SUCCESS_MESSAGE;
import static br.com.vinisolon.application.enums.Messages.USER_ALREADY_EXISTS_WITH_EMAIL;
import static br.com.vinisolon.application.enums.Messages.USER_NOT_FOUND;

@AllArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional
    public SuccessResponse create(CreateUserRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new BusinessRuleException(USER_ALREADY_EXISTS_WITH_EMAIL.getMessage());
        }

        User user = userMapper.requestToEntity(request);

        userRepository.save(user);

        return new SuccessResponse(DEFAULT_SUCCESS_MESSAGE.getMessage());
    }

    @Transactional
    public SuccessResponse update(UpdateUserRequest request) {
        User user = userRepository.findById(request.id())
                .orElseThrow(() -> new BusinessRuleException(USER_NOT_FOUND.getMessage()));

        if (!Objects.equals(request.email(), user.getEmail())
                && userRepository.existsByEmail(request.email())) {
            throw new BusinessRuleException(USER_ALREADY_EXISTS_WITH_EMAIL.getMessage());
        }

        userMapper.updateEntity(user, request);

        userRepository.save(user);

        return new SuccessResponse(DEFAULT_SUCCESS_MESSAGE.getMessage());
    }

    @Transactional
    public SuccessResponse delete(Long id) {
        userRepository.deleteById(id);

        return new SuccessResponse(DEFAULT_SUCCESS_MESSAGE.getMessage());
    }

    public UserResponse get(Long id) {
        return userRepository.findById(id)
                .map(userMapper::entityToResponse)
                .orElseThrow(() -> new BusinessRuleException(USER_NOT_FOUND.getMessage()));
    }

    public List<UserResponse> getAll() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::entityToResponse)
                .toList();
    }

}
