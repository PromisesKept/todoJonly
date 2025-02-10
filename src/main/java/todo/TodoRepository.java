package todo;

import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static todo.Main.scan;

@Getter
public class TodoRepository {

    private final List<Todo> list = new ArrayList<>();

    public void add(Todo todo) {
            list.add(todo);
    }

    public void delete(int id) {
        list.removeIf(todo -> todo.getId() == id);
    }

    public void edit() {
        System.out.println("Вот ваш список задач:");
        list.forEach(System.out::println);
        System.out.println();

        System.out.println("Введите ID задачи, которую хотите отредактировать?");
        int id = scan.nextInt();
        scan.nextLine();
        Optional<Todo> result = list.stream()
                .filter(t -> t.getId() == id)
                .findFirst();

        if (result.isPresent()) {
            Todo todo = result.get();
            updateName(todo);
            updateDescription(todo);
            updateDeadline(todo);
            updateStatus(todo);
        }
    }

    private void updateName(Todo todo) {
        System.out.println("Сейчас у этой задачи название: ");
        System.out.println(todo.getName());
        System.out.println();
        System.out.println("Введите новое название и нажмите Enter \n или не пишите ничего если не хотите менять его");
        String name = scan.nextLine();
        if (!name.isBlank()) {
            todo.setName(name);
            System.out.println("Название изменено!\n\n");
        }
    }

    private void updateDescription(Todo todo) {
        System.out.println("Сейчас у этой задачи описание: ");
        System.out.println(todo.getDescription());
        System.out.println();
        System.out.println("Введите новое описание и нажмите Enter \n или не пишите ничего если не хотите менять его");
        String description = scan.nextLine();
        if (!description.isBlank()) {
            todo.setDescription(description);
            System.out.println("Описание изменено!\n\n");
        }
    }

    private void updateDeadline(Todo todo) {
        System.out.println("Сейчас у этой задачи срок сдачи: ");
        System.out.println(todo.getDeadline());
        System.out.println();
        System.out.println("Введите новый срок сдачи в формате dd-MM-yyyy HH:mm и нажмите Enter \n или не пишите ничего если не хотите менять его");
        String dateTime = scan.nextLine();
        if (!dateTime.isBlank()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
            todo.setDeadline(LocalDateTime.parse(dateTime, formatter));
            System.out.println("Срок сдачи изменен!\n\n");
        }
    }

    private void updateStatus(Todo todo) {
        System.out.println("Сейчас у этой задачи статус: ");
        System.out.println(todo.getStatus());
        System.out.println();
        System.out.println("""
           Чтобы изменить статус введите:
           
           1 - чтобы поставить статус 'TODO'
           2 - чтобы поставить статус 'IN PROGRESS'
           3 - чтобы завершить задачу
           
           """);
        int status = scan.nextInt();
        scan.nextLine();
        switch (status) {
            case 1 -> todo.setStatus(Status.TODO);
            case 2 -> todo.setStatus(Status.IN_PROGRESS);
            case 3 -> todo.setStatus(Status.DONE);
            default -> System.out.println("Да нет такой цифры в списке! Чо ты, хакер, мамкин?!");
        }
        System.out.println("Статус изменен!\n\n");
    }

}
