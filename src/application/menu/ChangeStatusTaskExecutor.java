package application.menu;

import application.model.MenuCommand;
import application.model.Task;
import application.repository.TaskRepository;
import application.repository.UserRepository;

import java.util.HashMap;
import java.util.Map;

public class ChangeStatusTaskExecutor {
    private int currentMenuNumber = 1;
    private final Map<Integer, AbstractSubmenu> menus = new HashMap<>();
    private final Map<Integer, String> argumentsForMenu = new HashMap<>();
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    public ChangeStatusTaskExecutor(UserRepository userRepository, TaskRepository taskRepository) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
        menus.put(1, new UsersSubmenu(userRepository, taskRepository));
        menus.put(2, new TasksSubmenu(userRepository, taskRepository));
        menus.put(3, new StatusSubmenu(userRepository, taskRepository));
    }

    public MenuCommand executeNextSubmenu() {
        AbstractSubmenu currentMenu = menus.get(currentMenuNumber);
        String currentMenuResult = currentMenu.viewMenuAndGetClientInputString(argumentsForMenu.get(currentMenuNumber));
        if (currentMenuResult.equals("back")) {
            setPreviousSubmenu();
            return MenuCommand.OK;
        } else if (currentMenuResult.equals("exit")) {
            return MenuCommand.EXIT;
        } else {
            checkOnLastSubmenu(currentMenuResult);
            setInputArgumentForNextMenu(currentMenuResult);
            return MenuCommand.OK;
        }
    }

    private void setInputArgumentForNextMenu(String argument) {
        argumentsForMenu.put(++currentMenuNumber, argument);
    }

    private void setPreviousSubmenu() {
        if (currentMenuNumber > 1)
        currentMenuNumber--;
    }

    private void checkOnLastSubmenu(String statusTask) {
        if (currentMenuNumber == menus.size()) {
            String taskId = argumentsForMenu.get(currentMenuNumber);
            taskRepository.findTaskById(taskId).changeStatus(Task.Status.valueOf(statusTask));
            currentMenuNumber = 0;
        }
    }

}
