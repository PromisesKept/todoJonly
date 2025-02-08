package todo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static todo.Main.scan;

public class Service {

    private static final List<Todo> list = new ArrayList<Todo>();

    public static void info() {
        System.out.println("""
                add - добавить задачу
                list - вывести список задач
                edit - отредактировать задачу
                delete - удалить задачу
                filter - отфильтровать задачи по статусу
                sort - отсортировать задачи по статусу или сроку выполнения
                exit - не надо...
                
                Все операции описаны в неопределенном состоянии. В таком же они и выполняются.
                Например, "Задача была добавлена" - не значит, что она была добавлена успешно. Обратное равносильно.
              
                """);
    }

    public static void add() {
        System.out.println("Введите название задачи (после ввода нажмите Enter):");
        System.out.println();
        String name = scan.nextLine();

        System.out.println("Введите описание задачи (после ввода нажмите Enter):");
        String description = scan.nextLine();

        System.out.println("Введите срок завершения задачи (в формате dd-MM-yyyy HH:mm)");
        String dateTime = scan.nextLine();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        LocalDateTime deadline = LocalDateTime.parse(dateTime, formatter);

        System.out.println("Введите статус:");
        String status = scan.nextLine();

        System.out.printf("""
                            Вы действительно хотите добавить задачу?
                            
                            Название: %s
                            Описание: %s
                            Срок выполнения: %s
                            Статус: %s
                            
                            Для добавления нажмите 1, для отмены нажмите 0
                            
                            """, name, description, deadline, status);
        if (scan.nextInt() == 1) {
            list.add(new Todo(name, description, deadline, status));
            System.out.println("Задача была добавлена с ID:" + list.getLast().getId());
        } else

            System.out.println("Потом сделает кто-нибудь, пофиг...");
    }



    public static List<Todo> list() {
        return list;
    }

    public static String delete() {
        System.out.println("Введите ID задачи, которую хотите удалить?");
        int id = scan.nextInt();
        list.removeIf(todo -> todo.getId() == id);
        return "Задача была удалена!";
    }

    public static void edit() {
        System.out.println("Вот ваш список задач:");
        list().forEach(System.out::println);
        System.out.println();

        System.out.println("Введите ID задачи, которую хотите отредактировать?");
        int id = scan.nextInt();
        scan.nextLine();
        Optional<Todo> result = list.stream()
                .filter(t -> t.getId() == id)
                .findFirst();

       if (result.isPresent()) {
           System.out.println("Сейчас у этой задачи название: ");
           System.out.println(result.get().getName());
           System.out.println();
           System.out.println("Введите новое название и нажмите Enter \n или не пишите ничего если не хотите менять его");
           String name = scan.nextLine();
           if (!name.isBlank()) {
                result.get().setName(name);
                System.out.println("Название изменено!\n\n");
           }

           System.out.println("Сейчас у этой задачи описание: ");
           System.out.println(result.get().getDescription());
           System.out.println();
           System.out.println("Введите новое описание и нажмите Enter \n или не пишите ничего если не хотите менять его");
           String description = scan.nextLine();
           if (!name.isBlank()) {
               result.get().setDescription(description);
               System.out.println("Описание изменено!\n\n");
           }

           System.out.println("Сейчас у этой задачи срок сдачи: ");
           System.out.println(result.get().getDeadline());
           System.out.println();
           System.out.println("Введите новый срок сдачи в формате dd-MM-yyyy HH:mm и нажмите Enter \n или не пишите ничего если не хотите менять его");
           String dateTime = scan.nextLine();
           if (!dateTime.isBlank()) {
               DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
               result.get().setDeadline(LocalDateTime.parse(dateTime, formatter));
               System.out.println("Срок сдачи изменен!\n\n");
           }

           System.out.println("Сейчас у этой задачи статус: ");
           System.out.println(result.get().getStatus());
           System.out.println();
           System.out.println("Введите новый статус и нажмите Enter \n или не пишите ничего если не хотите менять его");
           String status = scan.nextLine();
           if (!status.isBlank()) {
               result.get().setStatus(status);
               System.out.println("Статус изменен!\n\n");
           }
       }

    }

    public static void filter() {
        System.out.println("Введите статус: ");
        String status = scan.nextLine();

        list.stream()
                .filter(a -> a.getStatus().equals(status))
                .forEach(System.out::println);
    }

    public static void sort() {
        // сортировка задач по сроку выполнения или статусу
        System.out.println("Вот ваш список задач:");
        list().forEach(System.out::println);
        System.out.println();
        System.out.println("Напишите: \n 1 чтобы отсортировать его по сроку выполнения \n 2 чтобы отсортировать его по статусу");
        int sorting = scan.nextInt();
        switch (sorting) {
            case 1 -> list().stream()
                    .sorted(Comparator.comparing(Todo::getDeadline))
                    .forEach(System.out::println);
            case 2 -> list().stream()
                    .sorted(Comparator.comparing(Todo::getStatus))
                    .forEach(System.out::println);
            default -> System.out.println("Ты чо, сук, не понял?! 1 или 2! \n Всё, диннах отсюдава...");
        }

    }



    public static void exit() {
        // выход из системы?
        System.out.println("Ну, все, вали! Чо ждешь?");
    }


}
