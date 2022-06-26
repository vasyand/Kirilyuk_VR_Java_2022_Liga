package application;

import application.menu.Filter;

import java.util.Scanner;
import java.util.Set;


public class InputStringValidator {
    private final static Scanner scanner = new Scanner(System.in);


    public String waitWhileClientWriteValidString(Set<String> validStrings, Filter filter) {
        while (true) {
            String input = scanner.nextLine();
            if ((input.equals("exit") || input.equals("back"))
                    || validStrings.contains(input)) {
                return input;
            }
            if (filter != null) {
                for (String s : validStrings) {
                    if (input.startsWith(s)) {
                        String[] filterAndArgs = input.split(" ");
                        if (filterAndArgs.length == 3) {
                            String inputFilter = filterAndArgs[1];
                            String filterArg = filterAndArgs[2];
                            if (inputFilter.equals(filter.getFlag())
                                    && filter.getValidValues().contains(filterArg)) {
                                return input;
                            }
                        }
                    }
                }
            }
            printMessageAboutMistake();
        }
    }

    private void printMessageAboutMistake() {
        System.out.println("Введено что-то не то, попробуй еще раз");
    }
}
