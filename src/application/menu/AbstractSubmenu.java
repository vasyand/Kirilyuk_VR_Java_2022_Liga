package application.menu;

import application.InputStringValidator;
import application.repository.TaskRepository;
import application.repository.UserRepository;

import java.util.Set;

public abstract class AbstractSubmenu {
    private final InputStringValidator inputStringValidator = new InputStringValidator();
    protected final UserRepository userRepository;
    protected final TaskRepository taskRepository;

    public AbstractSubmenu(UserRepository userRepository, TaskRepository taskRepository) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
    }

    public String viewMenuAndGetClientInputString(String argument) {
        if (!setStartOptions(argument)){
            return "back";
        }
        printMenu();
        return inputStringValidator.waitWhileClientWriteValidString(getValidInputStrings(), getFilter());
    }

    Filter getFilter() {
        return null;
    }

    abstract boolean setStartOptions(String argument);

    abstract void printMenu();

    abstract Set<String> getValidInputStrings();
}
