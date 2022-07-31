package ru.homework.tasktracker.specification;

import org.springframework.data.jpa.domain.Specification;
import ru.homework.tasktracker.model.entity.*;
import ru.homework.tasktracker.model.filter.UserFilter;

import javax.persistence.criteria.*;
import java.time.LocalDate;

import static org.springframework.data.jpa.domain.Specification.*;

public class UserSpecification {

    public static Specification<User> generateSpecificationByUserFilter(UserFilter userFilter) {
        return where(filterByFirstName(userFilter.getFirstName()))
                .and(filterByMiddleName(userFilter.getMiddleName()))
                .and(filterByLastName(userFilter.getLastName()))
                .and(filterByTask(userFilter));
    }

    private static Specification<User> filterByFirstName(String firstName) {
        if (firstName == null) return null;
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(User_.FIRST_NAME), firstName);
    }

    private static Specification<User> filterByMiddleName(String middleName) {
        if (middleName == null) return null;
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(User_.MIDDLE_NAME), middleName);
    }

    private static Specification<User> filterByLastName(String lastName) {
        if (lastName == null) return null;
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(User_.LAST_NAME), lastName);
    }

    private static Specification<User> filterByTask(UserFilter userFilter) {
        return (root, query, criteriaBuilder) -> {
            Join<User, Task> tasks = root.join(User_.tasks, JoinType.INNER);
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
