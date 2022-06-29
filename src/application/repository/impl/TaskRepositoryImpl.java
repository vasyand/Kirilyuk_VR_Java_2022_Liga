package application.repository.impl;

import application.Application;
import application.model.Task;
import application.model.TaskStatus;
import application.repository.TaskRepository;

import java.io.BufferedWriter;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TaskRepositoryImpl implements TaskRepository {
    private final static URL PATH = Application.class.getResource("tasks.csv");

    @Override
    public List<Task> findAll() {
        List<Task> tasks = new ArrayList<>();
        try {
            tasks = Files.lines(Paths.get(PATH.toURI()))
                    .filter(s -> !s.isEmpty())
                    .map(this::mapToTask)
                    .collect(Collectors.toList());
        } catch (Exception exception) {
            System.out.println("При загрузке тасок что-то пошло не так");
        }
        return tasks;
    }

    @Override
    public Task findById(int id) {
        return findAll().stream()
                .filter(t -> t.getId() == id)
                .findAny()
                .orElse(null);
    }

    @Override
    public List<Task> findByUserId(int userId) {
        return findAll().stream()
                .filter(t -> t.getUserId() == userId)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(int id) {
        List<String> tasks = findAll().stream()
                .filter(task -> task.getId() != id)
                .map(this::mapToString)
                .collect(Collectors.toList());
        try {
            Files.write(Paths.get(PATH.toURI()), tasks, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (Exception e) {
            System.out.println("При удалении что-то пошло не так");
        }
    }

    @Override
    public void save(Task task) {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(PATH.toURI()), StandardOpenOption.APPEND)) {
            writer.newLine();
            writer.write(mapToString(task));
        } catch (Exception e) {
            System.out.println("При сохранении что-то пошло не так");
        }
    }

    @Override
    public void update(Task task) {
        delete(task.getId());
        save(task);
    }

    @Override
    public void deleteAll() {
        try {
            Files.writeString(Paths.get(PATH.toURI()), "", StandardOpenOption.TRUNCATE_EXISTING);
            System.out.println("Файл с задачами очищен");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("При очистке файла что-то пошло не так");
        }
    }

    private Task mapToTask(String task) {
        String[] fields = task.split(",");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.M.y");
        TaskStatus status = TaskStatus.CREATED;
        if (fields.length == 6) {
            status = TaskStatus.valueOf(fields[5]);
        }
        return new Task(Integer.parseInt(fields[0]), fields[1], fields[2], Integer.parseInt(fields[3]),
                LocalDate.parse(fields[4], formatter), status);
    }

    private String mapToString(Task task) {
        return "\n" + task.getId() +
                "," +
                task.getTitle() +
                "," +
                task.getDescription() +
                "," +
                task.getUserId() +
                "," +
                task.getDate().format(DateTimeFormatter.ofPattern("d.M.y")) +
                "," +
                task.getTaskStatus();
    }
}
