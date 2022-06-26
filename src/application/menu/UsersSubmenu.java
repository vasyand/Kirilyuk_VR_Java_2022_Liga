package application.menu;

import application.model.Task;
import application.repository.TaskRepository;
import application.model.User;
import application.repository.UserRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.lang.System.out;

public class UsersSubmenu extends AbstractSubmenu {
    private List<User> users;

    public UsersSubmenu(UserRepository userRepository, TaskRepository taskRepository) {
        super(userRepository, taskRepository);
    }

    @Override
    boolean setStartOptions(String input) {
        users = userRepository.getUsers();
        return true;
    }

    @Override
    void printMenu() {
        out.println("Вот тебе список наших пользователей: ");
        users.forEach(out::println);
        out.println("\nЧтобы посмотреть, чем занимается каждый из них, введи id соответствующего пользователя!\n"
        + "Также ты можешь отфильтровать таски пользователя по статусу, для этого после id надо указать команду\n"
        + "-fs RUN/COMPLETED/CREATED (Например, 1 -fs RUN)");
    }

    @Override
    Set<String> getValidInputStrings() {
        return users.stream()
                .map(User::getId)
                .collect(Collectors.toSet());
    }

    @Override
    Filter getFilter() {
        return new Filter("-fs", Arrays.stream(Task.Status.values())
        .map(Enum::toString)
        .collect(Collectors.toSet()));
    }
}
