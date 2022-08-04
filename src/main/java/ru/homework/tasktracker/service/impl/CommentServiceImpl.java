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
import ru.homework.tasktracker.model.filter.CommentFilter;
import ru.homework.tasktracker.repository.CommentRepository;
import ru.homework.tasktracker.service.CommentService;

import javax.transaction.Transactional;

import static java.lang.String.format;
import static ru.homework.tasktracker.specification.CommentSpecification.generateSpecificationByCommentFilter;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    @Override
    public CommentFullDto findById(Long id) {
        Comment comment = this.findCommentById(id);
        return commentMapper.commentToCommentFullDto(comment);
    }

    @Override
    public Page<CommentFullDto> findAll(CommentFilter commentFilter, Pageable pageable) {
        Page<Comment> comments = commentRepository.findAll(generateSpecificationByCommentFilter(commentFilter), pageable);
        return comments.map(commentMapper::commentToCommentFullDto);
    }

    @Override
    public CommentFullDto save(CommentCreateDto commentCreateDto) {
        Comment comment = commentMapper.commentCreateDtoToComment(commentCreateDto);
        return commentMapper.commentToCommentFullDto(commentRepository.save(comment));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Comment comment = this.findCommentById(id);
        commentRepository.save(comment);
    }

    @Override
    @Transactional
    public CommentFullDto update(CommentUpdateDto commentUpdateDto, Long id) {
        Comment comment = this.findCommentById(id);
        commentMapper.commentUpdateDtoMergeWithComment(commentUpdateDto, comment);
        return commentMapper.commentToCommentFullDto(commentRepository.save(comment));
    }

    private Comment findCommentById(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(format("Комментария с id %s не существует", id)));
    }

}
