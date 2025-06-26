package org.example;

import org.example.Command.CollectionManager;
import org.example.Command.Command;
import org.example.Option.DragonCharacter;
import org.example.Other.Request;
import org.example.Other.Response;

public class CountByCharacterCommand implements Command {
    private CollectionManager collectionManager;

    public CountByCharacterCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    public Response execute(Request request) {
        try {
            StringBuilder charactersList = new StringBuilder("Доступные характеристики: ");
            for (DragonCharacter character : DragonCharacter.values()) {
                charactersList.append(character).append(" ");
            }
            System.out.println(charactersList.toString());

            System.out.print("Введите характеристику: ");
            String input = System.console().readLine().trim().toUpperCase();

            DragonCharacter character = DragonCharacter.valueOf(input);
            int count = collectionManager.countByCharacter(character);

            return new Response("Найдено элементов с характеристикой " + character + ": " + count);

        } catch (IllegalArgumentException e) {
            return new Response("Ошибка: неверная характеристика. Выберите из предложенных.");
        }
    }

    public String descr() {
        return "count_by_character - выводит количество элементов, значение поля character которых равен заданному";
    }
}
