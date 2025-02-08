package todo;

import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static todo.Main.scan;

public class TodoRepository {

    @Getter
    private List<Todo> list = new ArrayList<>();

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
            System.out.println("""
                   Чтобы изменить статус введите:
                   
                   1 - чтобы поставить статус 'TODO'
                   2 - чтобы поставить статус 'IN PROGRESS'
                   3 - чтобы завершить задачу
                   
                   """);
            int status = scan.nextInt();
            scan.nextLine();
            switch (status) {
                case 1 -> result.get().setStatus(Status.TODO);
                case 2 -> result.get().setStatus(Status.IN_PROGRESS);
                case 3 -> result.get().setStatus(Status.DONE);
                default -> System.out.println("Да нет такой цифры в списке! Чо ты, хакер, мамкин?!");
            }
            System.out.println("Статус изменен!\n\n");

        }

    }
}
