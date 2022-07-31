package ru.homework.tasktracker.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.homework.tasktracker.model.dto.CommentCreateDto;
import ru.homework.tasktracker.model.dto.CommentFullDto;
import ru.homework.tasktracker.model.dto.CommentUpdateDto;
import ru.homework.tasktracker.model.filter.CommentFilter;

public interface CommentService {
    CommentFullDto findById(Long id);

    Page<CommentFullDto> findAll(CommentFilter commentFilter, Pageable pageable);
    Long save(CommentCreateDto commentCreateDto);

    void delete(Long id);

    void update(CommentUpdateDto comment, Long id);
}
