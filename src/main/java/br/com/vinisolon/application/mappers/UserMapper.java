package br.com.vinisolon.application.mappers;

import br.com.vinisolon.application.entities.User;
import br.com.vinisolon.application.requests.UserRequest;
import br.com.vinisolon.application.responses.UserResponse;
import io.micrometer.common.util.StringUtils;
import org.mapstruct.BeanMapping;
import org.mapstruct.Condition;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    User requestToEntity(UserRequest request);

    UserResponse entityToResponse(User entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(@MappingTarget User entity, UserRequest request);

    @Condition
    default boolean isNotEmpty(String str) {
        return StringUtils.isNotEmpty(str);
    }

}
