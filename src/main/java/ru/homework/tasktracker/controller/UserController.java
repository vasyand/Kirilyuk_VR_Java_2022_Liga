package ru.homework.tasktracker.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.homework.tasktracker.model.dto.UserCreateDto;
import ru.homework.tasktracker.model.dto.UserFullDto;
import ru.homework.tasktracker.model.dto.UserUpdateDto;
import ru.homework.tasktracker.model.filter.UserFilter;
import ru.homework.tasktracker.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserFullDto> findById(@PathVariable Long id) {
        UserFullDto user = userService.findById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<UserFullDto>> findAll(@RequestBody UserFilter userFilter, Pageable pageable) {
        Page<UserFullDto> users = userService.findAll(userFilter, pageable);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Long> create(@RequestBody UserCreateDto userCreateDto) {
        Long id = userService.save(userCreateDto);
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/projects")
    public ResponseEntity<?> addProject(@PathVariable Long id, @RequestBody Long projectId) {
        userService.addProject(id, projectId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody UserUpdateDto userUpdateDto, @PathVariable Long id) {
        userService.update(userUpdateDto, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("user-analytics/search-with-max-number-tasks")
    public ResponseEntity<UserFullDto> searchWithMaxNumberTasks(@RequestBody UserFilter userFilter) {
        UserFullDto userFullDto = userService.findUserWithMaxNumberTasks(userFilter);
        return new ResponseEntity<>(userFullDto, HttpStatus.OK);
    }
}
