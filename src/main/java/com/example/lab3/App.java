package com.example.lab3;

import com.example.lab3.dao.*;
import com.example.lab3.entity.*;
import com.example.lab3.util.HibernateUtil;
import java.util.HashSet;
import java.util.Set;

import java.util.List;
import java.util.Scanner;

public class App {
    private static final TehnikaDao dao = new TehnikaDaoImpl();
    private static final VladelysDao vladelysDao  = new VladelysDaoImpl();
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n=== Главное меню ===");
            System.out.println("1 — Добавить технику");
            System.out.println("2 — Вывести всю технику");
            System.out.println("3 — Удалить объект");
            System.out.println("4 — Изменить объект");
            System.out.println("5 — Выполнить действие");
            System.out.println("6 - Добавить владельца");
            System.out.println("7 - Удалить владельца");
            System.out.println("8 - Показать всех владельцов");
            System.out.println("9 - Определить владельца техники");
            System.out.println("10 - Вывести человека по id");
            System.out.println("0 — Выход");
            System.out.print("Выбор (0–9): ");

            String choice = sc.nextLine();
            switch (choice) {
                case "1" -> addTehnika();
                case "2" -> list();
                case "3" -> delete();
                case "4" -> update();
                case "5" -> action();
                case "6" -> addVladelys();
                case "7" -> removeVladelys();
                case "8" -> showAllVladelys();
                case "9" ->linkOwnerToTehnika();
                case "10" -> showIdVladelys();
                case "0" -> {
                    HibernateUtil.shutdown();
                    return;
                }
                default -> System.out.println("Неверный выбор! Введите число от 0 до 8.");
            }
        }
    }


    private static void addTehnika() {
        System.out.print("Тип (1–боевая,2–гражданская): ");
        int type = Integer.parseInt(sc.nextLine());

        System.out.print("Модель: ");
        String model = sc.nextLine();
        System.out.print("Вес: ");
        double ves = Double.parseDouble(sc.nextLine());
        System.out.print("Цвет: ");
        String color = sc.nextLine();

        Tehnika t;
        if (type == 1) {
            BoevayaTehnika bt = new BoevayaTehnika();
            bt.setModel(model);
            bt.setVes(ves);
            bt.setColor(color);
            System.out.print("Огневая мощность: ");
            bt.setOgnevayaMoschnost(Integer.parseInt(sc.nextLine()));
            System.out.print("Объем обоймы: ");
            bt.setObemOboymy(Integer.parseInt(sc.nextLine()));
            t = bt;

        } else if (type == 2) {
            GrazhdanskayaTehnika gt = new GrazhdanskayaTehnika();
            gt.setModel(model);
            gt.setVes(ves);
            gt.setColor(color);
            System.out.print("Подогрев руля (0/1): ");
            gt.setPodogrevRulya(Boolean.parseBoolean(sc.nextLine()));
            System.out.print("Голосовой ассистент (0/1): ");
            gt.setGolosovoyAssistent(Boolean.parseBoolean(sc.nextLine()));
            t = gt;
        } else {
            System.out.println("Некорректный тип техники.");
            return;
        }

        dao.save(t);
        System.out.println("Техника добавлена.");
    }

    private static void list() {
        List<Tehnika> all = dao.findAll();
        if (all.isEmpty()) {
            System.out.println("Техники нет.");
            return;
        }
        System.out.println("\nВсе объекты:");
        for (int i = 0; i < all.size(); i++) {
            Tehnika t = all.get(i);
            System.out.printf("%d. %s | вес: %.2f | цвет: %s",
                    i + 1, t.getModel(), t.getVes(), t.getColor());
            if (t instanceof BoevayaTehnika bt) {
                System.out.printf(" | огневую мощность: %d | обойма: %d",
                        bt.getObemOboymy(), bt.getObemOboymy());
            } else if (t instanceof GrazhdanskayaTehnika gt) {
                System.out.printf(" | подогрев: %b | ассистент: %b",
                        gt.isPodogrevRulya(), gt.isGolosovoyAssistent());
            }
            System.out.println();
        }
    }

    private static void update() {
        int size = dao.findAll().size();
        System.out.printf("Введите номер для изменения (1–%d): ", size);
        int num = Integer.parseInt(sc.nextLine());
        List<Tehnika> all = dao.findAll();
        if (num < 1 || num > size) {
            System.out.println("Неверный номер.");
            return;
        }

        Tehnika t = all.get(num - 1);

        System.out.print("Новая модель: ");
        t.setModel(sc.nextLine());
        System.out.print("Новый вес: ");
        t.setVes(Double.parseDouble(sc.nextLine()));
        System.out.print("Новый цвет: ");
        t.setColor(sc.nextLine());

        if (t instanceof BoevayaTehnika bt) {
            System.out.print("Новая огневая мощность: ");
            bt.setOgnevayaMoschnost(Integer.parseInt(sc.nextLine()));
            System.out.print("Новый объем обоймы: ");
            bt.setObemOboymy(Integer.parseInt(sc.nextLine()));
        } else if (t instanceof GrazhdanskayaTehnika gt) {
            int pr;
            do {
                System.out.print("Новое значение подогрева руля (0/1): ");
                pr = Integer.parseInt(sc.nextLine());
                if (pr != 1 && pr != 1){
                    System.out.println("Неверное значение!");
                }
            } while (pr != 0 && pr != 1);
            boolean p = false;
            if (pr == 1) p = true;
            gt.setPodogrevRulya(p);
            int ga;
            do {
                System.out.print("Голосовой ассистент (0/1): ");
                ga = Integer.parseInt(sc.nextLine());
            } while (ga != 0 && ga != 1);
            p = false;
            if (ga == 1) p = true;
            gt.setGolosovoyAssistent(p);
        }

        dao.update(t);
        System.out.println("Объект обновлён.");
    }

    private static void delete() {
        int size = dao.findAll().size();
        System.out.printf("Введите номер для изменения (1–%d): ", size);
        int num = Integer.parseInt(sc.nextLine());
        List<Tehnika> all = dao.findAll();
        if (num < 1 || num > size) {
            System.out.println("Неверный номер.");
            return;
        }
        Tehnika t = all.get(num - 1);

        dao.delete(t.getId());
        System.out.println("Удаление выполнено (если ID существовал).");
    }

    private static void action() {
        int size = dao.findAll().size();
        System.out.printf("Введите номер объекта для выполнения действия (1–%d): ", size);
        int num = Integer.parseInt(sc.nextLine());
        List<Tehnika> all = dao.findAll();
        if (num < 1 || num > size) {
            System.out.println("Неверный номер.");
            return;
        }
        Tehnika t = all.get(num - 1);
        System.out.println("1 - Ехать вперед\n2 - Ехать назад\n3 - Издать сигнал\n4 - Обслужить технику");
        if (t instanceof BoevayaTehnika ) {
            System.out.println("5 — Стрелять\n6 — Перезарядиться");
        } else if (t instanceof GrazhdanskayaTehnika ) {
            System.out.println("5 — Включить аварийку\n6 — Использовать как такси");
        }
        System.out.print("Выбор: ");
        int act = Integer.parseInt(sc.nextLine());
        if (act > 0 &&  act <= 6) {
            if (act < 5){
                switch (act) {
                    case 1 -> System.out.println(t.ehatVpered());
                    case 2 -> System.out.println(t.ehatNazad());
                    case 3 -> System.out.println(t.signal());
                    case 4 -> System.out.println(t.obsluzhivanie());
                }
            }
            else{
                if (t instanceof BoevayaTehnika bt){
                    switch (act) {
                        case 5 -> System.out.println(bt.vystrel());
                        case 6 -> System.out.println(bt.perezaryadka());
                    }
                }
                else if (t instanceof GrazhdanskayaTehnika gt) {
                    switch (act) {
                        case 5 ->  System.out.println(gt.vklyuchitAvariyku());
                        case 6 ->  System.out.println(gt.ispolzovatKakTaxi());
                    }
                }
            }
        }
    }
    private static void addVladelys() {
        System.out.print("Имя владельца: "); String name = sc.nextLine();
        Vladelys v = new Vladelys();
        v.setName(name);
        vladelysDao.save(v);
        System.out.println("Владелец сохранён.");
    }

    private static void removeVladelys() {
        List<Vladelys> list = vladelysDao.findAll();
        if (list.isEmpty()) {
            System.out.println("Нечего удалять.");
            return;
        }
        showAllVladelys();
        System.out.printf("Введите номер владельца для удаления (1–%d): ", list.size());
        int idx = Integer.parseInt(sc.nextLine());
        if (idx < 1 || idx > list.size()) {
            System.out.println("Неверный номер.");
            return;
        }
        Vladelys v = list.get(idx - 1);
        vladelysDao.delete(v.getId());
        System.out.println("Владелец удалён.");
    }

//    private static void showAllVladelys() {
//        List<Vladelys> list = vladelysDao.findAll();
//        if (list.isEmpty()) {
//            System.out.println("Нет владельцев.");
//            return;
//        }
//        for (int i = 0; i < list.size(); i++) {
//            Vladelys v = list.get(i);
//            System.out.printf("%d) %s%n", i + 1, v);
//        }
//    }
private static void showIdVladelys() {
    System.out.print("Введите id владельца: ");
    long id = Long.parseLong(sc.nextLine());

    Vladelys v = vladelysDao.findID(id);
    System.out.printf("Владелец: %s%n", v.getName());
    Set<Tehnika> tehniki = v.getTehniki();
    if (tehniki.isEmpty()) {
        System.out.println("У этого владельца нет техники.");
    } else {
        System.out.println("Техника:");
        for (Tehnika t : tehniki) {
            System.out.println("  - " + t);
        }
    }

    }

    private static void showAllVladelys() {
        List<Vladelys> list = vladelysDao.getAll();
        if (list.isEmpty()) {
            System.out.println("Владельцев нету");
            return;
        }
        for (Vladelys v : list) {
            System.out.println("Владелец: " + v.getName());
            System.out.println("Техника:");
            for (Tehnika t : v.getTehniki()) {
                System.out.println("  -Модель:" + t.getModel() + "| Цвет:" + t.getColor());
            }
        }
    }
    private static void linkOwnerToTehnika() {
        // Сначала покажем список техники:
        List<Tehnika> techList = dao.findAll();
        if (techList.isEmpty()) {
            System.out.println("Нет техники для привязки.");
            return;
        }
        System.out.println("--- Техника ---");
        for (int i = 0; i < techList.size(); i++) {
            System.out.printf("%d) %s%n", i + 1, techList.get(i).getModel());
        }
        System.out.printf("Выберите номер техники (1–%d): ", techList.size());
        int techNum = Integer.parseInt(sc.nextLine());
        if (techNum < 1 || techNum > techList.size()) {
            System.out.println("Неверный номер.");
            return;
        }
        long techId = techList.get(techNum - 1).getId();


        List<Vladelys> ownerList = vladelysDao.findAll();
        if (ownerList.isEmpty()) {
            System.out.println("Нет владельцев для привязки.");
            return;
        }
        System.out.println("--- Владельцы ---");
        for (int i = 0; i < ownerList.size(); i++) {
            System.out.printf("%d) %s%n", i + 1, ownerList.get(i).getName());
        }
        System.out.printf("Выберите номер владельца (1–%d): ", ownerList.size());
        int ownerNum = Integer.parseInt(sc.nextLine());
        if (ownerNum < 1 || ownerNum > ownerList.size()) {
            System.out.println("Неверный номер.");
            return;
        }
        long ownerId = ownerList.get(ownerNum - 1).getId();
        System.out.println("Ожидайте...");

        dao.linkOwner(techId, ownerId);
        System.out.println("Владелец успешно привязан к технике.");
    }
}


