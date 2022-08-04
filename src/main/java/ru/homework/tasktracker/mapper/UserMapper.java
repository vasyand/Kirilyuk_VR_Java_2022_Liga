package ru.homework.tasktracker.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.homework.tasktracker.model.dto.UserCreateDto;
import ru.homework.tasktracker.model.dto.UserFullDto;
import ru.homework.tasktracker.model.dto.UserUpdateDto;
import ru.homework.tasktracker.model.entity.User;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = SPRING, uses = {
        ProjectMapper.class,
        TaskMapper.class
})
public interface UserMapper {
    UserFullDto userToUserFullDto(User user);

    User userCreateDtoToUser(UserCreateDto userCreateDto);

    @Mapping(target = "firstName", nullValuePropertyMappingStrategy = IGNORE)
    @Mapping(target = "middleName", nullValuePropertyMappingStrategy = IGNORE)
    @Mapping(target = "lastName", nullValuePropertyMappingStrategy = IGNORE)
    User userUpdateDtoMergeWithUser(UserUpdateDto userUpdateDto, @MappingTarget User user);

}
