package application.menu;

import application.model.Task;
import application.repository.TaskRepository;
import application.repository.UserRepository;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import static java.lang.System.out;

public class StatusSubmenu extends AbstractSubmenu{
    private Task currentTask;

    public StatusSubmenu(UserRepository userRepository, TaskRepository taskRepository) {
        super(userRepository, taskRepository);
    }


    @Override
    boolean setStartOptions(String taskId) {
        currentTask = taskRepository.findTaskById(taskId);
        return true;
    }

    @Override
    void printMenu() {
        out.println("\nКакой из следующих статусов присвоить? Введи статус полностью");
        getOtherStatusesForTask(currentTask).forEach(status -> out.println("   " + status));
    }

    @Override
    Set<String> getValidInputStrings() {
        Task.Status currentTaskStatus = currentTask.getStatus();
        return Arrays.stream(Task.Status.values())
                .filter(s -> s != currentTaskStatus)
                .map(Enum::toString)
                .collect(Collectors.toSet());
    }

    private Set<Task.Status> getOtherStatusesForTask(Task task) {
        return Arrays.stream(Task.Status.values())
                .filter(s -> s != task.getStatus())
                .collect(Collectors.toSet());
    }

}
