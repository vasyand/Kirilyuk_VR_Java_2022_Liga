package application;


import application.repository.TaskRepository;
import application.repository.UserRepository;
import application.service.ConsoleCommandExecutor;
import application.service.impl.TaskConsoleCommandExecutor;
import application.service.impl.UserConsoleCommandExecutor;

import java.util.HashMap;
import java.util.Map;

public class ConsoleCommandExecutorDispatcher {
    private final Map<String, ConsoleCommandExecutor> executors = new HashMap<>();

    public ConsoleCommandExecutorDispatcher(UserRepository userRepository, TaskRepository taskRepository) {
        executors.put("task", new TaskConsoleCommandExecutor(userRepository, taskRepository));
        executors.put("user", new UserConsoleCommandExecutor(userRepository));
    }

    public void resolveCommandExecutor(String input) {
        String[] inputArgs = input.split(" ");
        if (inputArgs.length == 1) {
            System.out.println("Кроме ключевого слова надо еще ввести комманду");
            return;
        }
        String executor = inputArgs[0];
        String inputWithoutExecutor = input.substring(executor.length()).trim();
        executors.get(executor).execute(inputWithoutExecutor);
    }
}
