package ru.efimkin.bredik.authservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import ru.efimkin.bredik.authservice.dto.UserDto;
import ru.efimkin.bredik.authservice.model.Users;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    UserDto toDto(Users user);
    Users toEntity(UserDto userDto);
}