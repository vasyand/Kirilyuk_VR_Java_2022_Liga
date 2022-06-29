package application;

import java.util.Scanner;

public class ConsoleReader {
    private final Scanner scanner = new Scanner(System.in);


    public String readCommand() {
        String input = scanner.nextLine();
        if (input.equals("exit") || input.startsWith("user")
                || input.startsWith("task") || input.equals("help"))
            return input;
        else {
            System.out.println("Команда обязательно должна начинаться с ключевого слова \"user\" или \"task\".\n" +
                    "Ну или \"exit\", если очень хочется выйти.");
            return "error";
        }
    }
}
