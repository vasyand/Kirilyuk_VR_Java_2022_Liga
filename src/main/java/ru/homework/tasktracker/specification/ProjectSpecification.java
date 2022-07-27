package ru.homework.tasktracker.specification;

import org.springframework.data.jpa.domain.Specification;
import ru.homework.tasktracker.model.entity.Project;
import ru.homework.tasktracker.model.entity.Project_;
import ru.homework.tasktracker.model.filter.ProjectFilter;

import static org.springframework.data.jpa.domain.Specification.where;

public class ProjectSpecification {

    public static Specification<Project> generateSpecificationByProjectFilter(ProjectFilter projectFilter) {
        return where(filterByTitle(projectFilter.getTitle()))
                .and(filterByDescription(projectFilter.getDescription()));
    }

    private static Specification<Project> filterByTitle(String title) {
        if (title == null) return null;
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get(Project_.TITLE), "%" + title + "%");
    }

    private static Specification<Project> filterByDescription(String description) {
        if (description == null) return null;
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get(Project_.DESCRIPTION), "%" + description + "%");
    }
}
