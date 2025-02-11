package todo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class ServiceTest {

    private Service service;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        service = new Service();
        System.setOut(new PrintStream(outContent));
    }

    @Test
    void testInfo() {
        service.info();
        String expectedOutput = """
                add - добавить задачу
                list - вывести список задач
                edit - отредактировать задачу
                delete - удалить задачу
                filter - отфильтровать задачи по статусу
                sort - отсортировать задачи по статусу или сроку выполнения
                exit - не надо...
                
                Все операции описаны в неопределенном состоянии. В таком же они и выполняются.
                Например, "Задача была добавлена" - не значит, что она была добавлена успешно. Обратное равносильно.
              
                """;
        assertEquals(expectedOutput.trim(), outContent.toString().trim());
    }

    @Test
    void testAdd() {
        String input = "Название задачи\nОписание задачи\n01-01-2023 12:00\n1\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);

        service.add(scanner);

        List<Todo> tasks = service.list();
        assertEquals(1, tasks.size());
        assertEquals("Название задачи", tasks.get(0).getName());
        assertEquals("Описание задачи", tasks.get(0).getDescription());
        assertEquals(LocalDateTime.parse("01-01-2023 12:00", DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")), tasks.get(0).getDeadline());
    }

    @Test
    void testList() {
        List<Todo> tasks = service.list();
        assertNotNull(tasks);
        assertTrue(tasks.isEmpty());
    }

    @Test
    void testFilter() {
        String input = "TODO\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);

        service.filter(scanner);

        // Проверяем вывод (если нужно)
        String expectedOutput = """
                По какому статус Вы хотите отфильтровать задачи?
          
                TODO - по статус 'TODO'
                IN PROGRESS - статус 'IN PROGRESS'
                DONE - только завершенные задачи
                
                """;
        assertEquals(expectedOutput.trim(), outContent.toString().trim());
    }


}