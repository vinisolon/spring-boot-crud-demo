package br.com.vinisolon.application.mappers;

import br.com.vinisolon.application.entities.User;
import br.com.vinisolon.application.requests.CreateUserRequest;
import br.com.vinisolon.application.requests.UpdateUserRequest;
import br.com.vinisolon.application.responses.UserResponse;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    User requestToEntity(CreateUserRequest request);

    UserResponse entityToResponse(User entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(@MappingTarget User entity, UpdateUserRequest request);

}
