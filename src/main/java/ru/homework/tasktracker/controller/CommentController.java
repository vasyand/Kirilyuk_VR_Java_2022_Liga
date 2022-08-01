package ru.homework.tasktracker.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.homework.tasktracker.model.dto.CommentCreateDto;
import ru.homework.tasktracker.model.dto.CommentFullDto;
import ru.homework.tasktracker.model.dto.CommentUpdateDto;
import ru.homework.tasktracker.model.filter.CommentFilter;
import ru.homework.tasktracker.service.CommentService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/comments")
public class CommentController {
    private final CommentService commentService;

    @GetMapping("/{id}")
    public ResponseEntity<CommentFullDto> findById(@PathVariable Long id) {
        CommentFullDto commentFullDto = commentService.findById(id);
        return new ResponseEntity<>(commentFullDto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<CommentFullDto>> findAll(@RequestBody(required = false) CommentFilter commentFilter,
                                                        Pageable pageable) {
        Page<CommentFullDto> comments = commentService.findAll(commentFilter, pageable);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Long> create(@RequestBody @Valid CommentCreateDto commentCreateDto) {
        Long id = commentService.save(commentCreateDto);
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }

    @PreAuthorize("@authorizeValidator.thisTaskBelongToUser(#id)")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id,
                                    @RequestBody CommentUpdateDto commentUpdateDto) {
        commentService.update(commentUpdateDto, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("@authorizeValidator.thisTaskBelongToUser(#id)")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        commentService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
