package ru.homework.tasktracker.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.homework.tasktracker.model.entity.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
}
