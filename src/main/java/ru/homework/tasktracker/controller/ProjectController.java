package ru.homework.tasktracker.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.homework.tasktracker.model.dto.ProjectFullDto;
import ru.homework.tasktracker.model.dto.ProjectPostDto;
import ru.homework.tasktracker.model.filter.ProjectFilter;
import ru.homework.tasktracker.service.ProjectService;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/projects")
public class ProjectController {
    private final ProjectService projectService;

    @GetMapping("/{id}")
    public ResponseEntity<ProjectFullDto> findById(@PathVariable Long id) {
        ProjectFullDto projectFullDto = projectService.findById(id);
        return new ResponseEntity<>(projectFullDto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<ProjectFullDto>> findAll(@RequestBody(required = false) ProjectFilter projectFilter,
                                                        Pageable pageable) {
        Page<ProjectFullDto> projects = projectService.findAll(projectFilter, pageable);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Long> create(@RequestBody ProjectPostDto projectPostDto) {
        Long id = projectService.save(projectPostDto);
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id,
                                         @RequestBody ProjectPostDto projectPostDto) {
        projectService.update(projectPostDto, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        projectService.delete(id);
        return new ResponseEntity<>( HttpStatus.OK);
    }
}
