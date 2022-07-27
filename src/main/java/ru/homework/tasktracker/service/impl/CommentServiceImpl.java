package ru.homework.tasktracker.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.homework.tasktracker.model.entity.Comment;
import ru.homework.tasktracker.model.filter.CommentFilter;
import ru.homework.tasktracker.repository.CommentRepository;
import ru.homework.tasktracker.service.CommentService;

import java.util.List;

import static ru.homework.tasktracker.specification.CommentSpecification.generateSpecificationByCommentFilter;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;

    @Override
    public Comment findById(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(String.format("Комментария с id %s не существует", id)));
    }

    @Override
    public List<Comment> findAll(CommentFilter commentFilter) {
        return commentRepository.findAll(generateSpecificationByCommentFilter(commentFilter));
    }

    @Override
    public void save(Comment comment) {
        commentRepository.save(comment);
    }

    @Override
    public void delete(Long id) {
        Comment comment = this.findById(id);
        commentRepository.save(comment);
    }

    @Override
    public void update(Comment comment) {
        this.findById(comment.getId());
        commentRepository.save(comment);
    }
}
