package todo;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ServiceTest {

    @Test
    public void testInfo() {
        // Перенаправляем вывод System.out в ByteArrayOutputStream
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        Service.info();

        // Проверяем его вывод
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

        // Восстанавливаем вывод в консоль
        System.setOut(System.out);
    }
}
