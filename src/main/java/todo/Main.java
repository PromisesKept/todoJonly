package todo;

import java.util.Scanner;

public class Main {

    public static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {

        System.out.println("\n\n ПриПЕТ! \n\n" +
                "Ты попал в консоль величайшей разработки человечества - TODO В КОНСОЛИ ЕБАТЬ! \n" +
                "В 2025 году нахой, ага! \n" +
                "Возрадуйся прогрессу человеческому, ирод и пиши команды! \n" +
                "А если не знаешь команд (НУ ТЫ И ДААААА, КОНЕЧНО, В 2к45 НЕ ЗНАТЬ КОМАНД...), \n то по-старинке пиши info хуле! \n" +
                "Кто знает, тот знает. Кто не знает, тот не знает! \n АУФ! \n" +
                "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ \n");


        String type = scan.next();
        scan.nextLine();
        while (!"exit".equals(type)) {
            switch (type) {
                case "info" -> Service.info();
                case "add" -> Service.add();
                case "list" -> Service.list().forEach(System.out::println);
                case "delete" -> System.out.println(Service.delete());
                case "edit" -> Service.edit();
                case "filter" -> Service.filter();
                case "sort" -> Service.sort();

            }
            type = scan.next();
            scan.nextLine();
        }
        scan.close();



    }

}