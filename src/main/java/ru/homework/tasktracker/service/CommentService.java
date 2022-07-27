package ru.homework.tasktracker.service;

import ru.homework.tasktracker.model.entity.Comment;
import ru.homework.tasktracker.model.filter.CommentFilter;

import java.util.List;

public interface CommentService {
    Comment findById(Long id);

    List<Comment> findAll(CommentFilter commentFilter);
    void save(Comment comment);

    void delete(Long id);

    void update(Comment comment);
}
