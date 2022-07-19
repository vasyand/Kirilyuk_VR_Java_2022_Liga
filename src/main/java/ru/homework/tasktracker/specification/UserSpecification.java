package ru.homework.tasktracker.specification;

import org.springframework.data.jpa.domain.Specification;
import ru.homework.tasktracker.model.entity.*;
import ru.homework.tasktracker.model.filter.UserFilter;

import javax.persistence.criteria.*;
import java.time.LocalDate;

public class UserSpecification {

    public static Specification<User> getUserById(Long id) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(User_.id), id);
    }

    public static Specification<User> generateSpecificationByUserFilter(UserFilter userFilter) {
        return (root, query, criteriaBuilder) -> {
            ListJoin<User, Task> tasks = root.join(User_.tasks);
            return criteriaBuilder.and(
                    filterByTaskStatus(userFilter.getTaskStatus(), tasks, criteriaBuilder),
                    filterByTaskDates(userFilter.getFrom(), userFilter.getTo(), tasks, criteriaBuilder)
            );
        };
    }

    private static Predicate filterByTaskStatus(TaskStatus status, Join<User, Task> join, CriteriaBuilder criteriaBuilder) {
        if (status == null) return criteriaBuilder.and();
        return criteriaBuilder.equal(join.get(Task_.TASK_STATUS), status);

    }

    private static Predicate filterByTaskDates(LocalDate from, LocalDate to, Join<User, Task> join, CriteriaBuilder criteriaBuilder) {
        if (from == null || to == null) return criteriaBuilder.and();
        return criteriaBuilder.between(join.get(Task_.DATE), from, to);
    }
}
