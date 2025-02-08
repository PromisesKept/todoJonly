package todo;

import java.util.Scanner;

public class Main {

    public static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {



        String type = scan.next();
        while (!"exit".equals(type)) {
            switch (type) {
                case "info" ->  Service.info();
                case "add" -> System.out.println(Service.add());
                case "list" -> Service.list().forEach(System.out::println);
                case "delete" -> System.out.println(Service.delete());
                case "edit" -> Service.edit();
                case "filter" -> Service.filter();
                case "sort" -> Service.sort();

            }
            type = scan.next();
        }
        scan.close();



    }

}