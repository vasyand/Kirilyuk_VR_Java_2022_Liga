package ru.homework.tasktracker.publisher;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.homework.tasktracker.model.Event;
import ru.homework.tasktracker.subscriber.Subscriber;
import ru.homework.tasktracker.subscriber.TaskSubscriber;
import ru.homework.tasktracker.subscriber.UserSubscriber;

import java.time.format.DateTimeParseException;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class EventPublisher {
    private final Map<String, UserSubscriber> userEventSubscribers;
    private final Map<String, TaskSubscriber> taskEventSubscribers;

    public void publishEvent(Event event) {
        if (event.getType().equals("help")) {
            printHelpMessage();
        }
        String prefix = event.getType().equals("user") ? "user-" : "task-";
        try {
            Subscriber subscriber = null;
            if (event.getType().equals("user")) {
                subscriber = userEventSubscribers.get(prefix + event.getCommand());
            } else {
                subscriber = taskEventSubscribers.get(prefix + event.getCommand());
            }
            if (subscriber == null) {
                throw new RuntimeException("Такой команды не существует!");
            }
            subscriber.execute(event);
        } catch (DateTimeParseException ex) {
            System.out.println("Дата должна быть в формате 12.01.2022");
        } catch (NumberFormatException ex) {
            System.out.println("Id должен быть числовым!");
        } catch (RuntimeException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void printHelpMessage() {
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
                "    Примеры команд: \"viewt 1\", \"viewt 1 -fs=RUN\"\n" +
                "3. \"create\" - создание пользователя. После команды надо ввести перечень аргументов, расположенных в строгом порядке: имя\n" +
                "    Пример команды: \"create Вася\"\n" +
                "4. \"edit\" - изменение пользователя. После команды надо ввести id пользователя, которого собираешься изменять,\n" +
                "    а затем перечень аргументов, расположенных в строгом порядке: имя\n" +
                "    Примеры команд: \"edit 1 Петя\"\n" +
                "5. \"delete\" - удаление пользователя. После команды надо ввести id пользователя, которого собираешься удалить\n";
        String taskRules = "Команды сервиса \"task\":\n" +
                "1. \"view\" - просмотр задач. Можно отфильтровать задачи по статусу, для это надо после команды ввести фильтр: \"-fs=status(CREATED/RUN/COMPLETED)\"\n" +
                "    Чтобы вывести определнную задачу, после команды надо ввести ее id.\n" +
                "    Примеры команд: \"view\", \"view 1\", \"view -fs=RUN\"\n" +
                "2. \"create\" - создание задачи. После команды надо ввести перечень аргументов, расположенных в строгом порядке: заголовок,описание,id пользователя,дедлайн(формат: 01.01.2022)\n" +
                "    Пример команды: \"create выполнить дз,написать много кода,4,30.06.2022\"\n" +
                "3. \"edit\" - изменение задачи. После команды надо ввести id задачи, которую собираешься изменять,\n" +
                "    а затем перечень аргументов, расположенных в строгом порядке: заголовок,описание,id пользователя,дедлайн(формат: 01.01.2022),статус(CREATED/RUN/COMPLETED)\n" +
                "    если не хочешь менять какой-то аргумент, то просто подставь знак точки \".\"\n" +
                "    Примеры команд: \"edit 1 выполнить дз,написать много кода,4,30.06.2022\", \"edit 1 .,написать немного кода,.,.,RUN\"\n" +
                "4. \"delete\" - удаление задачи. После команды надо ввести id задачи, которую собираешься удалить.\n";
        System.out.println(mainRules);
        System.out.println(userRules);
        System.out.println(taskRules);
    }
}
