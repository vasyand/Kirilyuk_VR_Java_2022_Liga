package application.menu;

import application.model.Task;
import application.model.User;
import application.repository.TaskRepository;
import application.repository.UserRepository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.lang.System.out;

public class TasksSubmenu extends AbstractSubmenu {
    private List<Task> tasks;
    private User user;


    public TasksSubmenu(UserRepository userRepository, TaskRepository taskRepository) {
        super(userRepository, taskRepository);
    }


    @Override
    boolean setStartOptions(String input) {
        String[] args = input.split(" ");
        String userId = args[0];
        user = userRepository.findUserById(userId);
        if (args.length > 1) {
            String statusFilter = args[2];
            tasks = user.getTasks().stream()
                    .filter(task -> task.getStatus().toString().equals(statusFilter))
                    .collect(Collectors.toList());
        }
        else {
            tasks = user.getTasks();
        }
        return checkFillingTasks();
    }

    private boolean checkFillingTasks() {
        if (tasks.isEmpty()){
            out.println("У этого юзера нет тасок (тасок с таким фильтром) \n");
            return false;
        }
        return true;
    }

    @Override
    void printMenu() {
        out.println("\n" + user.getName() + " выполняет следюущие задачи: ");
        tasks.forEach(out::println);
        out.println("\nВведи id задачи, у которой хочешь поменять статус ");
    }

    @Override
    Set<String> getValidInputStrings() {
        return tasks.stream()
                .map(Task::getId)
                .collect(Collectors.toSet());
    }

}
