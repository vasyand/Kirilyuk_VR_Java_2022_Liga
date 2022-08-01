package ru.homework.tasktracker.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.homework.tasktracker.exception.EntityNotFoundException;
import ru.homework.tasktracker.mapper.CommentMapper;
import ru.homework.tasktracker.model.dto.CommentCreateDto;
import ru.homework.tasktracker.model.dto.CommentFullDto;
import ru.homework.tasktracker.model.dto.CommentUpdateDto;
import ru.homework.tasktracker.model.entity.Comment;
import ru.homework.tasktracker.model.entity.Project;
import ru.homework.tasktracker.model.filter.CommentFilter;
import ru.homework.tasktracker.repository.CommentRepository;
import ru.homework.tasktracker.service.CommentService;

import javax.transaction.Transactional;
import java.util.List;

import static java.lang.String.*;
import static ru.homework.tasktracker.mapper.CommentMapper.*;
import static ru.homework.tasktracker.specification.CommentSpecification.generateSpecificationByCommentFilter;
import static ru.homework.tasktracker.specification.ProjectSpecification.generateSpecificationByProjectFilter;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;

    @Override
    @Transactional
    public CommentFullDto findById(Long id) {
        Comment comment = this.findCommentById(id);
        return commentToCommentFullDto(comment);
    }

    @Override
    @Transactional
    public Page<CommentFullDto> findAll(CommentFilter commentFilter, Pageable pageable) {
        Page<Comment> comments;
        if (commentFilter != null) {
            comments = commentRepository.findAll(generateSpecificationByCommentFilter(commentFilter), pageable);
        } else {
            comments = commentRepository.findAll(pageable);
        }
        return comments.map(CommentMapper::commentToCommentFullDto);
    }

    @Override
    @Transactional
    public Long save(CommentCreateDto commentCreateDto) {
        Comment comment = commentCreateDtoToComment(commentCreateDto);
        return commentRepository.save(comment).getId();
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Comment comment = this.findCommentById(id);
        commentRepository.save(comment);
    }

    @Override
    @Transactional
    public void update(CommentUpdateDto commentUpdateDto, Long id) {
        Comment comment = this.findCommentById(id);
        commentUpdateDtoMergeWithComment(commentUpdateDto, comment);
        commentRepository.save(comment);
    }

    private Comment findCommentById(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(format("Комментария с id %s не существует", id)));
    }
}
