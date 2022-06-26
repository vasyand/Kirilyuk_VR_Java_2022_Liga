package application.repository;

import application.model.Task;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class TaskRepository {
    private final URL PATH = TaskRepository.class.getClassLoader().getResource("resources/tasks.csv");
    private List<Task> tasks;

    public List<Task> getTasks() {
        if (tasks == null) {
            loadTasks();
        }
        return tasks;
    }

    private void loadTasks() {
        try {
            tasks = Files.lines(Paths.get(PATH.toURI()))
                    .map(this::mapToTask)
                    .collect(Collectors.toList());
        } catch (Exception exception) {
            System.out.println("При загрузке тасок что-то пошло не так: " + exception.getMessage());
        }
    }

    private Task mapToTask(String task) {
        String[] fields = task.split(",");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.M.y");
        return new Task(fields[0], fields[1], fields[2], fields[3], LocalDate.parse(fields[4], formatter));
    }

    public Task findTaskById(String id) {
        return getTasks().stream()
                .filter(t -> t.getId().equals(id))
                .findAny()
                .get();
    }

    public List<Task> findTasksByUserId(String userId) {
        return getTasks().stream()
                .filter(t -> t.getUserId().equals(userId))
                .collect(Collectors.toList());
    }

}
