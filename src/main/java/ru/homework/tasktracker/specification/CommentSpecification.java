package ru.homework.tasktracker.specification;

import org.springframework.data.jpa.domain.Specification;
import ru.homework.tasktracker.model.entity.Comment;
import ru.homework.tasktracker.model.entity.Comment_;
import ru.homework.tasktracker.model.filter.CommentFilter;

import static org.springframework.data.jpa.domain.Specification.where;

public class CommentSpecification {

    public static Specification<Comment> generateSpecificationByCommentFilter(CommentFilter commentFilter) {
        return where(filterByMessage(commentFilter.getMessage()))
                .and(filterByTaskId(commentFilter.getTaskId()));
    }

    private static Specification<Comment> filterByMessage(String message) {
        if (message == null) return null;
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get(Comment_.MESSAGE), "%" + message + "%");
    }

    private static Specification<Comment> filterByTaskId(String taskId) {
        if (taskId == null) return null;
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(Comment_.TASK), taskId);
    }
}
