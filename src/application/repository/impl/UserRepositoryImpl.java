package application.repository.impl;

import application.Application;
import application.model.Task;
import application.model.User;
import application.repository.TaskRepository;
import application.repository.UserRepository;

import java.io.BufferedWriter;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserRepositoryImpl implements UserRepository {
    private final static URL PATH = Application.class.getResource("users.csv");
    private final TaskRepository taskRepository;

    public UserRepositoryImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public User findById(int id) {
        return findAll().stream()
                .filter(u -> u.getId() == id)
                .findAny()
                .orElse(null);
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        try {
            users = Files.lines(Paths.get(PATH.toURI()))
                    .filter(s -> !s.isEmpty())
                    .map(this::mapToUser)
                    .peek(u -> u.setTasks(taskRepository.findByUserId(u.getId())))
                    .collect(Collectors.toList());
        } catch (Exception exception) {
            System.out.println("При загрузке юзеров что-то пошло не так");
        }
        return users;
    }

    @Override
    public void update(User user) {
        List<Task> tasks = user.getTasks();
        delete(user.getId());
        tasks.forEach(taskRepository::save);
        save(user);
    }

    @Override
    public void save(User user) {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(PATH.toURI()), StandardOpenOption.APPEND)) {
            writer.newLine();
            writer.write(mapToString(user));
        } catch (Exception e) {
            System.out.println("При сохранении что-то пошло не так");
        }
    }

    @Override
    public void delete(int id) {
        User deletedUser = findById(id);
        deletedUser.getTasks().forEach(t -> taskRepository.delete(t.getId()));
        List<String> users = findAll().stream()
                .filter(u -> u.getId() != id)
                .map(this::mapToString)
                .collect(Collectors.toList());
        try {
            Files.write(Paths.get(PATH.toURI()), users, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (Exception e) {
            System.out.println("При удалении что-то пошло не так");
        }
    }

    @Override
    public void deleteAll() {
        try {
            Files.writeString(Paths.get(PATH.toURI()),"", StandardOpenOption.TRUNCATE_EXISTING);
            taskRepository.deleteAll();
            System.out.println("Файл с пользователями очищен");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("При очистке файла что-то пошло не так");
        }
    }

    private User mapToUser(String user) {
        String[] fields = user.split(",");
        return new User(Integer.parseInt(fields[0]), fields[1]);
    }

    private String mapToString(User user) {
        return user.getId() +
                "," +
                user.getName();
    }
}
