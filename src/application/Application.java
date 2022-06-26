package application;

import application.model.MenuCommand;
import application.repository.TaskRepository;
import application.repository.UserRepository;
import application.menu.ChangeStatusTaskExecutor;

public class Application {
    private static ChangeStatusTaskExecutor changeStatusTaskExecutor;

    public static void main(String[] args) {
        TaskRepository taskRepository = new TaskRepository();
        UserRepository userRepository = new UserRepository(taskRepository);
        changeStatusTaskExecutor = new ChangeStatusTaskExecutor(userRepository, taskRepository);
        startApplication();
    }

    private static void startApplication() {
        printGreetingMessage();
        while (true) {
            MenuCommand command = changeStatusTaskExecutor.executeNextSubmenu();
            if (command == MenuCommand.EXIT) {
                break;
            }
        }
    }

    private static void printGreetingMessage() {
        System.out.println("Привет! Если хочешь вернуться в предыдущее меню, набери back. \n"
        + "А если вдруг захочешь закрыть программу, то введи exit \n");
    }

}
