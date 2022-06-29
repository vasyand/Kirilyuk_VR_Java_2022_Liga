package application;

import application.repository.impl.TaskRepositoryImpl;
import application.repository.impl.UserRepositoryImpl;

public class Application {
    public static void main(String[] args) {
        TaskRepositoryImpl taskRepository = new TaskRepositoryImpl();
        UserRepositoryImpl userRepository = new UserRepositoryImpl(taskRepository);
        ConsoleCommandExecutorDispatcher dispatcher = new ConsoleCommandExecutorDispatcher(userRepository, taskRepository);
        ConsoleReader consoleReader = new ConsoleReader();
        start(consoleReader, dispatcher);
    }

    private static void start(ConsoleReader reader, ConsoleCommandExecutorDispatcher dispatcher) {
        System.out.println("Привет! Если не знаешь, что можно вводить, напиши help.");
        while (true) {
            String command = reader.readCommand();
            if (command.equals("exit")) {
                break;
            }
            if (command.equals("error")) {
                continue;
            }
            if (command.equals("help")) {
                printHelpMessage();
                continue;
            }
            dispatcher.resolveCommandExecutor(command);
        }
    }

    private static void printHelpMessage() {
        String mainRules = "Обращения к программе могут начинаться со следующих ключевых слов:\n" +
                "1. \"user\" - обращение к сервису пользователей;\n" +
                "2. \"task\" - обращение к сервису задач;\n" +
                "3. \"exit\" - выход из программы.\n" +
                "При обращении к сервисам после ключего слова надо указать команду, разрешенную соответствующим сервисом.";
        String userRules = "Команды сервиса \"user\":\n" +
                "1. \"view\" - просмотр пользователей. Чтобы вывести определнного пользователя, после команды надо ввести его id.\n" +
                "    Примеры команд: \"view\", \"view 1\"\n" +
                "2. \"viewt\" - просмотр задач пользователя. После команды надо ввести id пользователя, чьи задачи ты хочешь посмотреть\n"  +
                "    Можно отфильтровать задачи по статусу, для это надо после id ввести фильтр: \"-t=status(CREATED/RUN/COMPLETED)\"\n" +
                "    Примеры команд: \"viewt 1\", \"viewt 1 -t=RUN\"\n" +
                "3. \"create\" - создание пользователя. После команды надо ввести перечень аргументов, расположенных в строгом порядке: id,имя\n" +
                "    Пример команды: \"create 1,Вася\"\n" +
                "4. \"edit\" - изменение пользователя. После команды надо ввести id пользователя, которого собираешься изменять,\n" +
                "    а затем перечень аргументов, расположенных в строгом порядке: имя\n" +
                "    Примеры команд: \"edit 1 Петя\"\n" +
                "5. \"delete\" - удаление пользователя. После команды надо ввести id пользователя, которого собираешься удалить\n" +
                "6. \"clean\" - очистка файла.";
        String taskRules = "Команды сервиса \"task\":\n" +
                "1. \"view\" - просмотр задач. Можно отфильтровать задачи по статусу, для это надо после команды ввести фильтр: \"-fs=status(CREATED/RUN/COMPLETED)\"\n" +
                "    Чтобы вывести определнную задачу, после команды надо ввести ее id.\n" +
                "    Примеры команд: \"view\", \"view 1\", \"view -fs=RUN\"\n" +
                "2. \"create\" - создание задачи. После команды надо ввести перечень аргументов, расположенных в строгом порядке: id,заголовок,описание,id пользователя,дедлайн(формат: 01.01.2022)\n" +
                "    Пример команды: \"create 1,выполнить дз,написать много кода,4,30.06.2022\"\n" +
                "3. \"edit\" - изменение задачи. После команды надо ввести id задачи, которую собираешься изменять,\n" +
                "    а затем перечень аргументов, расположенных в строгом порядке: заголовок,описание,id пользователя,дедлайн(формат: 01.01.2022),статус(CREATED/RUN/COMPLETED)\n" +
                "    если не хочешь менять какой-то аргумент, то просто подставь знак точки \".\"\n" +
                "    Примеры команд: \"edit 1 выполнить дз,написать много кода,4,30.06.2022\", \"edit 1 .,написать немного кода,.,.,RUN\"\n" +
                "4. \"delete\" - удаление задачи. После команды надо ввести id задачи, которую собираешься удалить.\n" +
                "5. \"clean\" - очистка файла.\n";
        System.out.println(mainRules);
        System.out.println(userRules);
        System.out.println(taskRules);
    }
}
