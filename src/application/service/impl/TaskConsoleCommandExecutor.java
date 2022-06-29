package application.service.impl;


import application.model.Task;
import application.model.TaskStatus;

import application.repository.TaskRepository;
import application.repository.UserRepository;
import application.service.ConsoleCommandExecutor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class TaskConsoleCommandExecutor implements ConsoleCommandExecutor {
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    public TaskConsoleCommandExecutor(UserRepository userRepository, TaskRepository taskRepository) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
    }

    @Override
    public void execute(String input) {
        String command = input.split(" ")[0];
        String inputWithoutCommand = input.substring(command.length()).trim();
        switch (command) {
            case "view" -> view(inputWithoutCommand);
            case "edit" -> edit(inputWithoutCommand);
            case "delete" -> delete(inputWithoutCommand);
            case "create" -> create(inputWithoutCommand);
            case "clean" -> clean();
            default -> System.out.format("Команды %s не существует\n", command);
        }
    }

    private void clean() {
        taskRepository.findAll();
    }

    private void view(String command) {
        if (command.isEmpty()) {
            List<Task> tasks = taskRepository.findAll();
            if(tasks.isEmpty()) {
                System.out.println("Задач вообще нет");
            }
            else {
                tasks.forEach(System.out::println);
            }
        } else if (!command.startsWith("-")) {
            String taskId = command.split(" ")[0];
            if (isNotInteger(taskId) || taskNotExists(taskId)) return;
            Task task = taskRepository.findById(Integer.parseInt(taskId));
            System.out.println(task);
        } else {
            viewWithFilter(command);
        }
    }

    private void edit(String command) {
        if (argIsEmpty(command, "А какую задачу менять-то надо?")
                || argAfterIdIsAbsent(command.split(" ").length)
                || isNotInteger(command.split(" ")[0])
                || taskNotExists(command.split(" ")[0])) {
            return;
        }
        String taskIdString = command.split(" ")[0];
        String updatedFields = command.substring(taskIdString.length()).trim();
        Task task = taskRepository.findById(Integer.parseInt(taskIdString));
        merge(updatedFields, task);
    }

    private void create(String command) {
        if (argIsEmpty(command, "Для создания задачи надо ввести поля")
                || invalidQuantityFields(command)
                || isNotInteger(command.split(",")[0])
                || taskExists(command.split(",")[0])
                || isNotInteger(command.split(",")[3])
                || userNotExist(command.split(",")[3])
                || invalidDate(command.split(",")[4])) {
            return;
        }

        String[] taskFields = command.split(",");
        taskRepository.save(new Task(Integer.parseInt(taskFields[0]),
                taskFields[1],
                taskFields[2],
                Integer.parseInt(taskFields[3]),
                LocalDate.parse(taskFields[4], DateTimeFormatter.ofPattern("d.M.y")),
                TaskStatus.CREATED));
        System.out.format("Задача с id %s создана\n", taskFields[0]);
    }

    private void delete(String command) {
        if (argIsEmpty(command, "Для удаления задачи после команды надо ввести ее id")
                || isNotInteger(command.split(" ")[0])
                || taskNotExists(command.split(" ")[0])) {
            return;
        }
        int taskId = Integer.parseInt(command.split(" ")[0]);
        taskRepository.delete(taskId);
        System.out.format("Задача с id %s успешно удалена\n", taskId);
    }

    private void merge(String updatedFields, Task task) {
        String[] args = updatedFields.split(",");
        if (!args[0].equals(".")) {
            task.setTitle(args[0]);
        }
        if (!args[1].equals(".")) {
            task.setDescription(args[1]);
        }
        if (!args[2].equals(".")) {
            if (isNotInteger(args[2]) || userNotExist(args[2])) return;
            task.setUserId(Integer.parseInt(args[2]));
        }
        if (!args[3].equals(".")) {
            if (invalidDate(args[3])) return;
            task.setDate(LocalDate.parse(args[3], DateTimeFormatter.ofPattern("d.M.y")));
        }
        if (!args[4].equals(".")) {
            if (!TaskStatus.getStatusSet().contains(args[4])) return;
            task.setTaskStatus(TaskStatus.valueOf(args[4]));
        }
        taskRepository.update(task);
        System.out.format("Задача с id %s обновлена\n", task.getId());
    }

    private void viewWithFilter(String command) {
        String[] filterWithArg = command.split("=");
        String filter = filterWithArg[0];
        if (!filter.equals("-fs")) {
            System.out.format("Фильтра %s не существует. Есть пока только фильтр \"-fs\"\n", filter);
        } else if (filterWithArg.length == 1) {
            System.out.println("А параметр для фильтра-то кто вводить будет????");
        } else {
            String status = filterWithArg[1].trim();
            if (statusNotExist(status)) return;
            List<Task> tasks = taskRepository.findAll().stream()
                    .filter(task -> task.getTaskStatus().toString().equals(status))
                    .collect(Collectors.toList());
            if (tasks.isEmpty()) {
                System.out.format("Задач со статусом %s сейчас нет\n", status);
            } else {
                tasks.forEach(System.out::println);
            }
        }
    }

    private boolean statusNotExist(String status) {
        Set<String> statuses = TaskStatus.getStatusSet();
        if (!statuses.contains(status)) {
            System.out.format("Статуса %s не существует\n", status);
            return true;
        }
        return false;
    }

    private boolean argAfterIdIsAbsent(int quantityArgs) {
        if (quantityArgs == 1) {
            System.out.println("Для редактирования надо ввести аргументы");
            return true;
        }
        return false;
    }

    private boolean invalidDate(String stringDate) {
        LocalDate date = getLocalDateOrNull(stringDate);
        return date == null;
    }

    private boolean invalidQuantityFields(String command) {
        String[] taskFields = command.split(",");
        if (taskFields.length < 5) {
            System.out.println("Введено слишком мало полей для задачи");
            return true;
        } else if (taskFields.length > 5) {
            System.out.println("Введено слишком много полей для задачи");
            return true;
        }
        return false;
    }

    private boolean argIsEmpty(String arg, String message) {
        if (arg.isEmpty()) {
            System.out.println(message);
            return true;
        }
        return false;
    }

    private boolean taskNotExists(String id) {
        if (taskRepository.findById(Integer.parseInt(id)) == null) {
            System.out.format("Задачи с id %s не существует\n", id);
            return true;
        }
        return false;
    }

    private boolean taskExists(String id) {
        if (taskRepository.findById(Integer.parseInt(id)) != null) {
            System.out.format("Задача с id %s уже существует\n", id);
            return true;
        }
        return false;
    }

    private boolean isNotInteger(String arg) {
        try {
            Integer.parseInt(arg);
            return false;
        } catch (NumberFormatException e) {
            System.out.format("%s - это не число. Id может быть только числовым!\n", arg);
            return true;
        }
    }

    private boolean userNotExist(String id) {
        if (userRepository.findById(Integer.parseInt(id)) == null) {
            System.out.format("Пользователя с id %s не существует\n", id);
            return true;
        }
        return false;
    }


    private LocalDate getLocalDateOrNull(String arg) {
        LocalDate localDate = null;
        try {
            localDate = LocalDate.parse(arg, DateTimeFormatter.ofPattern("d.M.y"));
        } catch (DateTimeParseException exception) {
            System.out.println("Дату надо вводить в формате 01.01.2022");
        }
        return localDate;
    }
}
