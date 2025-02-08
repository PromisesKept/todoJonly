package todo;

import java.util.Scanner;

public class Main {

    public static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {

        System.out.println("\n\n ПриПЕТ! \n\n" +
                "Ты попал в консоль величайшей разработки человечества - TODO В КОНСОЛИ! \n" +
                "В 2025 году, ага! \n" +
                "Возрадуйся прогрессу человеческому, ирод и пиши команды! \n" +
                "А если не знаешь команд (НУ ТЫ И ДААААА, КОНЕЧНО, В 2к45 НЕ ЗНАТЬ КОМАНД...), то пиши info \n" +
                "Кто знает, тот знает. Кто не знает, тот не знает! \n АУФ! \n" +
                "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ \n");


        String type = scan.next();
        scan.nextLine();
        Service service = new Service();

        while (!"exit".equals(type)) {
            switch (type) {
                case "info" -> service.info();
                case "add" -> service.add();
                case "list" -> service.list().forEach(System.out::println);
                case "delete" -> System.out.println(service.delete());
                case "edit" -> service.edit();
                case "filter" -> service.filter();
                case "sort" -> service.sort();

            }
            type = scan.next();
            scan.nextLine();
        }
        scan.close();



    }

}