package ru.homework.tasktracker.mapper;

import ru.homework.tasktracker.model.dto.UserCreateDto;
import ru.homework.tasktracker.model.dto.UserFullDto;
import ru.homework.tasktracker.model.dto.UserUpdateDto;
import ru.homework.tasktracker.model.entity.User;

import java.util.stream.Collectors;

import static ru.homework.tasktracker.model.entity.Role.USER;

public class UserMapper {
    public static UserFullDto userToUserFullDto(User user){
        UserFullDto userFullDto = new UserFullDto();
        userFullDto.setId(user.getId());
        userFullDto.setEmail(user.getEmail());
        userFullDto.setPassword(user.getPassword());
        userFullDto.setFirstName(user.getFirstName());
        userFullDto.setMiddleName(user.getMiddleName());
        userFullDto.setLastName(user.getLastName());
        userFullDto.setRole(user.getRole());
        userFullDto.setTasks(user.getTasks().stream()
                .map(TaskMapper::taskToTaskFullDto)
                .collect(Collectors.toList()));
        userFullDto.setProjects(user.getProjects().stream()
                .map(ProjectMapper::projectToProjectFullDto)
                .collect(Collectors.toList()));
        return userFullDto;
    }
    public static User userCreateDtoToUser(UserCreateDto userCreateDto){
        User user = new User();
        user.setEmail(userCreateDto.getEmail());
        user.setPassword(userCreateDto.getPassword());
        user.setFirstName(userCreateDto.getFirstName());
        user.setMiddleName(userCreateDto.getMiddleName());
        user.setLastName(userCreateDto.getLastName());
        user.setRole(USER);
        return user;
    }
    public static void userUpdateDtoMergeWithUser(UserUpdateDto userUpdateDto, User user){
        if (userUpdateDto.getFirstName() != null) {
            user.setFirstName(user.getFirstName());
        }
        if (userUpdateDto.getMiddleName() != null) {
            user.setMiddleName(userUpdateDto.getMiddleName());
        }
        if (userUpdateDto.getLastName() != null) {
            user.setLastName(userUpdateDto.getLastName());
        }
    }
}
