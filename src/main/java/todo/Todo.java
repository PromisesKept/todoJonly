package todo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Todo {

    private static int counter = 0;

    private int id;
    private String name;
    private String description;
    private LocalDateTime deadline;
    private Status status;

    public Todo(String name, String description, LocalDateTime deadline) {
        this.id = ++counter;
        this.name = name;
        this.description = description;
        this.deadline = deadline;
        this.status = Status.TODO;
    }

}
