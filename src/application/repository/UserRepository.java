package application.repository;

import application.model.User;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class UserRepository {
    private final URL PATH = TaskRepository.class.getClassLoader().getResource("resources/users.csv");
    private List<User> users;
    private final TaskRepository taskRepository;

    public UserRepository(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<User> getUsers() {
        if (users == null) {
            loadUsers();
        }
        return users;
    }

    private void loadUsers() {
        try {
            users = Files.lines(Paths.get(PATH.toURI()))
                    .map(this::mapToUser)
                    .collect(Collectors.toList());
        } catch (Exception exception) {
            System.out.println("При загрузке юзеров что-то пошло не так: " + exception.getMessage());
        }
    }

    private User mapToUser(String user) {
        String[] fields = user.split(",");
        String userId = fields[0];
        return new User(userId, fields[1], taskRepository.findTasksByUserId(userId));
    }

    public User findUserById(String id) {
        return getUsers().stream()
                .filter(u -> u.getId().equals(id))
                .findAny()
                .get();
    }
}
