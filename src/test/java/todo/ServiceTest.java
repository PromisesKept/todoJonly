package todo;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ServiceTest {

    @Mock
    private TodoRepository todoRepository;

    @InjectMocks
    private Service service;

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final java.io.InputStream originalIn = System.in;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        // Перенаправляем вывод System.out в ByteArrayOutputStream
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
        System.setIn(originalIn); // Восстанавливаем вывод в консоль
    }

    @Test
    public void testInfo() {
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
              
                """.trim();

        assertEquals(expectedOutput, outContent.toString().trim());

    }

    @Test
    void testAdd() {
        String input = "Название задачи\nОписание задачи\n01-01-2023 12:00\n1\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        LocalDateTime expectedDeadline = LocalDateTime.parse("01-01-2023 12:00", DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));

        service.add(scanner);

        String expectedOutput = """
            Введите название задачи (после ввода нажмите Enter):
            
            Введите описание задачи (после ввода нажмите Enter):
            Введите срок завершения задачи (в формате dd-MM-yyyy HH:mm)
            Вы действительно хотите добавить задачу?
            
            Название: Название задачи
            Описание: Описание задачи
            Срок выполнения: 2023-01-01T12:00
            
            Для добавления нажмите 1, для отмены нажмите 0
            
            Задача была добавлена с ID:1
            """;
        assertEquals(expectedOutput.trim(), outContent.toString().trim());

        verify(todoRepository, times(1)).add(new Todo("Название задачи", "Описание задачи", expectedDeadline));
    }

    @Test
    void testList() {
        List<Todo> expectedList = Arrays.asList(
                new Todo("Задача 1", "Описание 1", LocalDateTime.now()),
                new Todo("Задача 2", "Описание 2", LocalDateTime.now())
        );

        when(todoRepository.getList()).thenReturn(expectedList);
        List<Todo> result = service.list();
        assertEquals(expectedList, result);
        verify(todoRepository, times(1)).getList();
    }


}
