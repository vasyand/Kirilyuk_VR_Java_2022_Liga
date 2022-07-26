package ru.homework.tasktracker.specification;

import org.springframework.data.jpa.domain.Specification;
import ru.homework.tasktracker.model.entity.Task;
import ru.homework.tasktracker.model.entity.TaskStatus;
import ru.homework.tasktracker.model.entity.Task_;
import ru.homework.tasktracker.model.filter.TaskFilter;

import java.time.LocalDate;

import static org.springframework.data.jpa.domain.Specification.where;

public class TaskSpecification {

    public static Specification<Task> generateSpecificationByTaskFilter(TaskFilter taskFilter) {
        return where(filterByTitle(taskFilter.getTitle()))
                .and(filterByDescription(taskFilter.getDescription()))
                .and(filterByUserId(taskFilter.getUserId()))
                .and(filterByProjectId(taskFilter.getProjectId()))
                .and(filterByStatus(taskFilter.getTaskStatus()))
                .and(filterByDates(taskFilter.getFrom(), taskFilter.getTo()));
    }

    private static Specification<Task> filterByTitle(String title) {
        if (title == null) return null;
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get(Task_.TITLE), "%" + title + "%");
    }

    private static Specification<Task> filterByDescription(String description) {
        if (description == null) return null;
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get(Task_.DESCRIPTION), "%" + description + "%");
    }

    private static Specification<Task> filterByUserId(String userId) {
        if (userId == null) return null;
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(Task_.USER), userId);
    }

    private static Specification<Task> filterByProjectId(String projectId) {
        if (projectId == null) return null;
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(Task_.PROJECT), projectId);
    }

    private static Specification<Task> filterByStatus(TaskStatus status) {
        if (status == null) return null;
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(Task_.TASK_STATUS), status);
    }

    private static Specification<Task> filterByDates(LocalDate from, LocalDate to) {
        if (from == null || to == null) return null;
        return (root, query, criteriaBuilder) -> criteriaBuilder.between(root.get(Task_.DATE), from, to);
    }
}
