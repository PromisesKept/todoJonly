package todo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public final class Service {

    private final TodoRepository todoRepository = new TodoRepository();

    public void info() {
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

    public void add(Scanner scan) {
        System.out.println("Введите название задачи (после ввода нажмите Enter):");
        System.out.println();
        String name = scan.nextLine();

        System.out.println("Введите описание задачи (после ввода нажмите Enter):");
        String description = scan.nextLine();

        System.out.println("Введите срок завершения задачи (в формате dd-MM-yyyy HH:mm)");
        String dateTime = scan.nextLine();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        LocalDateTime deadline = LocalDateTime.parse(dateTime, formatter);

        System.out.printf("""
                            Вы действительно хотите добавить задачу?
                            
                            Название: %s
                            Описание: %s
                            Срок выполнения: %s
                            
                            Для добавления нажмите 1, для отмены нажмите 0
                            
                            """, name, description, deadline);
        if (scan.nextInt() == 1) {
            todoRepository.add(new Todo(name, description, deadline));
            System.out.println("Задача была добавлена с ID:" + todoRepository.getList().getLast().getId());
        } else {
            System.out.println("Потом сделает кто-нибудь, пофиг...");
        }
    }

    public List<Todo> list() {
        return todoRepository.getList();
    }

    public String delete(Scanner scan) {
        System.out.println("Введите ID задачи, которую хотите удалить?");
        int id = scan.nextInt();
        scan.nextLine();
        todoRepository.delete(id);
        return "Задача была удалена!";
    }

    public void edit() {
        todoRepository.edit();
    }

    public void filter(Scanner scan) {

        System.out.println("""
                По какому статус Вы хотите отфильтровать задачи?
          
                TODO - по статус 'TODO'
                IN PROGRESS - статус 'IN PROGRESS'
                DONE - только завершенные задачи
                
                """);
        String statusInput = scan.nextLine();

        Status status;
        try {
            status = Status.valueOf(statusInput.toUpperCase().replace(" ", "_"));
        } catch (IllegalArgumentException e) {
            System.out.println("Может, раскладку переключить? М? Или чо ты там написать не можешь?");
        }

    }

    public void sort(Scanner scan) {
        // сортировка задач по сроку выполнения или статусу
        System.out.println("Вот ваш список задач:");
        list().forEach(System.out::println);
        System.out.println();
        System.out.println("Напишите: \n 1 чтобы отсортировать его по сроку выполнения \n 2 чтобы отсортировать его по статусу");
        int sorting = scan.nextInt();
        switch (sorting) {
            case 1 -> todoRepository.getList().stream()
                    .sorted(Comparator.comparing(Todo::getDeadline))
                    .forEach(System.out::println);
            case 2 -> todoRepository.getList().stream()
                    .sorted(Comparator.comparing(Todo::getStatus))
                    .forEach(System.out::println);
            default -> System.out.println("Ты чо, сук, не понял?! 1 или 2! \n Всё, диннах отсюдава...");
        }

    }



}
