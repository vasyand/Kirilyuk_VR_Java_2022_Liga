package ru.homework.tasktracker.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.homework.tasktracker.model.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
