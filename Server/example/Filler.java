package org.example;

import org.example.Option.*;
import org.example.Other.ClassReader;

import java.util.Date;

public class Filler {

    public Filler() {
    }

    public Dragon fill() {
        String name;
        int age;
        Coordinates coordinates;
        int wingspan;
        Color color;
        DragonCharacter dragonCharacter;
        Person killer;
        while (true) {
            System.out.print("Введите имя дракона: ");
            name = ClassReader.consoleReadLine();
            if (name.isEmpty()) {
                System.out.println("Имя не должно быть пустым");
            } else {
                break;
            }
        }
        while (true) {
            try {
                System.out.print("Введите координаты: ");
                String[] coordinate = ClassReader.consoleReadLine().split(" ");
                if (coordinate.length < 2) {
                    System.out.println("Введите две координаты через пробел");
                    continue;
                }
                Double x = Double.parseDouble(coordinate[0]);
                int y = Integer.parseInt(coordinate[1]);
                coordinates = (new Coordinates(x, y));
                break;
            } catch (NumberFormatException e) {
                System.out.println("Введите числовое значение");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
        while (true) {
            try {
                System.out.print("Введите возраст дракона: ");
                age = Integer.parseInt(ClassReader.consoleReadLine());
                if (age <= 0) {
                    System.out.println("Введите значение больше нуля");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Введите числовое значение");
            }
        }
        while (true) {
            try {
                System.out.print("Введите размах крыльев дракона: ");
                wingspan = Integer.parseInt(ClassReader.consoleReadLine());
                if (wingspan <= 0) {
                    System.out.println("Введите значение больше нуля");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Введите числовое значение");
            }
        }
        while (true) {
            try {
                System.out.print("Выберите цвет дракона из списка: ");
                for (Color t : Color.values()) {
                    System.out.print(t + " ");
                }
                System.out.print("Выберите цвет: ");
                String input = ClassReader.consoleReadLine().trim().toUpperCase();
                color = Color.valueOf(input);
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Выберите одно из предложенных значений");
            }
        }
        while (true) {
            try {
                System.out.print("Выберите характер дракона из списка: ");
                for (DragonCharacter t : DragonCharacter.values()) {
                    System.out.print(t + " ");
                }
                System.out.print("Выберите характер: ");
                String input = ClassReader.consoleReadLine().trim().toUpperCase();
                dragonCharacter = DragonCharacter.valueOf(input);
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Выберите одно из предложенных значений");
            }
        }


        while (true) {
            try {
                System.out.print("Хотите указать убийцу? (да/нет): ");
                String choice = ClassReader.consoleReadLine().trim().toLowerCase();
                if (choice.equals("да")) {
                    killer = fillKillerData();
                    return new Dragon(IDGenerator.generateID(), name, coordinates, new Date(), age, wingspan, color, dragonCharacter, killer);
                } else if (choice.equals("нет")) {
                    return new Dragon(IDGenerator.generateID(), name, coordinates, new Date(), age, wingspan, color, dragonCharacter, null);
                } else {
                    throw new IllegalArgumentException();
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Выберите одно из предложенных значений");
            }
        }
    }

    private Person fillKillerData() {
        String name;
        double weight;
        String passportID;
        Color eyeColor;
        while (true) {
            System.out.print("Введите имя убийцы: ");
            name = ClassReader.consoleReadLine().trim();
            if (!name.isEmpty()) {
                break;
            }
            System.out.println("Имя не должно быть пустым");
        }
        while (true) {
            try {
                System.out.print("Введите вec убийцы: ");
                weight = Double.parseDouble(ClassReader.consoleReadLine());
                if (weight <= 0) {
                    System.out.println("Введите значение больше нуля");
                } else {
                    break;
                }

            } catch (NumberFormatException e) {
                System.out.println("Введите числовое значение");
            }
        }
        while (true) {
            try {
                System.out.print("Введите ID убийцы: ");
                passportID = ClassReader.consoleReadLine().trim();
                if (passportID.isEmpty()) {
                    System.out.println("ID не может быть пустым");
                } else if (passportID.length() > 43) {
                    System.out.println("Длина ID не должна превышать 43 символа");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Введите числовое значение");
            }
        }
        while (true) {
            try {
                System.out.print("Выберите цвет глаз убийцы дракона из списка: ");
                for (Color t : Color.values()) {
                    System.out.print(t + " ");
                }
                System.out.print("Выберите цвет: ");
                String input = ClassReader.consoleReadLine().trim().toUpperCase();
                eyeColor = Color.valueOf(input);
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Выберите одно из предложенных значений");
            }
        }
        return new Person(name, weight, passportID, eyeColor);
    }

    public Dragon fill(ClassReader reader) {
        String name = reader.readLine();
        int age;
        Coordinates coordinates;
        int wingspan;
        Color color;
        DragonCharacter dragonCharacter;
        Person killer;
        if (name.isEmpty()) {
            return null;
        }
        try {
            String[] coordinate = reader.readLine().split(" ");
            if (coordinate.length < 2) {
                return null;
            }
            Double x = Double.parseDouble(coordinate[0]);
            int y = Integer.parseInt(coordinate[1]);
            coordinates = new Coordinates(x, y);
        } catch (NumberFormatException e) {
            return null;
        } catch (IllegalArgumentException e) {
            return null;
        }
        try {
            age = Integer.parseInt(reader.readLine());
            if (age <= 0) {
                return null;
            }
        } catch (NumberFormatException e) {
            return null;
        }

        try {
            wingspan = Integer.parseInt(reader.readLine());
            if (wingspan <= 0) {
                return null;
            }
        } catch (NumberFormatException e) {
            return null;
        }

        try {
            String input = reader.readLine().trim().toUpperCase();
            color = Color.valueOf(input);
        } catch (IllegalArgumentException e) {
            return null;
        }

        try {
            String input = reader.readLine().trim().toUpperCase();
            dragonCharacter = DragonCharacter.valueOf(input);
        } catch (IllegalArgumentException e) {
            return null;
        }

        String choice = reader.readLine().trim().toLowerCase();
        if (choice.equals("да")) {
            killer = fillKillerData(reader);
            if (killer == null) return null;
            return new Dragon(IDGenerator.generateID(), name, coordinates, new Date(), age, wingspan, color, dragonCharacter, killer);
        } else if (choice.equals("нет")) {
            return new Dragon(IDGenerator.generateID(), name, coordinates, new Date(), age, wingspan, color, dragonCharacter, null);
        } else return null;
    }

    private Person fillKillerData(ClassReader reader) {
        String name;
        double weight;
        String passportID;
        Color eyeColor;
        name = reader.readLine().trim();
        try {
            weight = Double.parseDouble(reader.readLine());
            if (weight <= 0) {
                return null;
            }
        } catch (NumberFormatException e) {
            return null;
        }

        try {
            passportID = reader.readLine().trim();
            if (passportID.isEmpty()) {
                return null;
            } else if (passportID.length() > 43) {
                return null;
            }
        } catch (NumberFormatException e) {
            return null;
        }

        try {
            String input = reader.readLine().trim().toUpperCase();
            eyeColor = Color.valueOf(input);
        } catch (IllegalArgumentException e) {
            return null;
        }
        return new Person(name, weight, passportID, eyeColor);
    }
}
