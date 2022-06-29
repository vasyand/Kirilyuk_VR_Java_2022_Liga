package application.service.impl;

import application.model.Task;
import application.model.TaskStatus;
import application.model.User;
import application.repository.UserRepository;
import application.service.ConsoleCommandExecutor;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class UserConsoleCommandExecutor implements ConsoleCommandExecutor {
    private final UserRepository userRepository;

    public UserConsoleCommandExecutor(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void execute(String input) {
        String command = input.split(" ")[0];
        String inputWithoutCommand = input.substring(command.length()).trim();
        switch (command) {
            case "view" -> view(inputWithoutCommand);
            case "viewt" -> viewt(inputWithoutCommand);
            case "edit" -> edit(inputWithoutCommand);
            case "delete" -> delete(inputWithoutCommand);
            case "create" -> create(inputWithoutCommand);
            case "clean" -> clean();
            default -> System.out.format("Команды %s не существует\n", command);
        }
    }

    private void clean() {
        userRepository.deleteAll();
    }


    private void view(String command) {
        if (command.isEmpty()) {
            List<User> users = userRepository.findAll();
            if(users.isEmpty()) {
                System.out.println("Пользователей вообще нет");
            }
            else {
                users.forEach(System.out::println);
            }
        } else {
            String[] args = command.split(" ");
            String userId = args[0];
            if (userIdNotIntegerOrUserNotExist(userId)) return;
            User user = userRepository.findById(Integer.parseInt(userId));
            System.out.println(user);
        }
    }

    private void viewt(String command) {
        if (argIsEmpty(command, "У какого пользователя таски смотреть?")
                || userIdNotIntegerOrUserNotExist(command.split(" ")[0])) {
            return;
        }
        String[] args = command.split(" ");
        User user = userRepository.findById(Integer.parseInt(args[0]));
        if (args.length == 1) {
            if (user.getTasks().isEmpty()) {
                System.out.format("У пользователя %s нет задач\n", user.getName());
            }
            else user.getTasks().forEach(System.out::println);
        }
        else {
            viewWithFilter(user, args[1]);
        }
    }

    private void edit(String command) {
        if (argIsEmpty(command, "А какого пользователя менять-то надо?")
                || argAfterIdIsAbsent(command.split(" ").length)
                || userIdNotIntegerOrUserNotExist(command.split(" ")[0])) {
            return;
        }
        String userId = command.split(" ")[0];
        String updatedFields = command.substring(userId.length()).trim();
        User user = userRepository.findById(Integer.parseInt(userId));
        merge(updatedFields, user);
    }

    private void create(String command) {
        if (argIsEmpty(command, "Для создания пользователя надо ввести поля")
                || invalidQuantityFields(command)
                || userIdNotIntegerOrUserNotExist(command.split(",")[0])) {
            return;
        }
        String[] userFields = command.split(",");
        userRepository.save(new User(Integer.parseInt(userFields[0]),
                userFields[1]));
        System.out.format("Пользователь с id %s создан\n", userFields[0]);
    }

    private void delete(String command) {
        if (argIsEmpty(command, "Для удаления пользователя псоле команды надо ввести его id")
                || userIdNotIntegerOrUserNotExist(command.split(" ")[0])) {
            return;
        }
        int userId = Integer.parseInt(command.split(" ")[0]);
        userRepository.delete(userId);
        System.out.format("Пользователь с id %s успешно удален\n", userId);
    }

    private void merge(String updatedFields, User user) {
        String[] args = updatedFields.split(",");
        if (!args[0].equals(".")) {
            user.setName(args[0]);
        }
        userRepository.update(user);
        System.out.format("Пользователь с id %s обновлен\n", user.getId());
    }

    private void viewWithFilter(User user, String command) {
        String[] filterWithArg = command.split("=");
        String filter = filterWithArg[0];
        if (!filter.equals("-t")) {
            System.out.format("Фильтра %s не существует. Есть пока только фильтр \"-t=status(CREATED,RUN,COMPLETED)\"\n", filter);
        } else if (filterWithArg.length == 1) {
            System.out.println("А параметр для фильтра-то кто вводить будет????");
        } else {
            String status = filterWithArg[1].trim();
            if (statusNotExist(status)) return;
            List<Task> tasks = user.getTasks().stream()
                    .filter(task -> task.getTaskStatus().toString().equals(status))
                    .collect(Collectors.toList());
            if (tasks.isEmpty()) {
                System.out.format("Тасок со статусом %s у пользователя %s сейчас нет\n", status, user.getName());
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

    private boolean invalidQuantityFields(String command) {
        String[] taskFields = command.split(",");
        if (taskFields.length < 2) {
            System.out.println("Введено слишком мало полей для пользователя");
            return true;
        } else if (taskFields.length > 2) {
            System.out.println("Введено слишком много полей для пользователя");
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

    private boolean userIdNotIntegerOrUserNotExist(String id) {
        int userId = getIntegerOrMinusOne(id);
        if (userId == -1) return true;
        if (userRepository.findById(userId) == null) {
            System.out.format("Пользователя с id %s не существует\n", userId);
            return true;
        }
        return false;
    }

    private int getIntegerOrMinusOne(String arg) {
        int taskId = -1;
        try {
            taskId = Integer.parseInt(arg);
        } catch (NumberFormatException e) {
            System.out.format("%s - это не число. Id может быть только числовым!\n", arg);
        }
        return taskId;
    }
}
