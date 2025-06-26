package org.example.Command;

import org.example.Other.DBRouter;
import org.example.Other.Request;
import org.example.Other.Response;
import org.example.Option.Dragon;

import java.util.ArrayList;
import java.util.List;

public class RemoveGreaterCommand implements Command {
    private final CollectionManager collectionManager;

    public RemoveGreaterCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    public Response execute(Request request) {
        int age;
        try {
            age = Integer.parseInt(request.parameter());
        } catch (NumberFormatException e) {
            return new Response("Введите числовое значение возраста!");
        }

        String currentUser = request.userLogin();
        int countBefore = collectionManager.size();

        // Собираем id всех подходящих драконов
        List<Long> idsToRemove = new ArrayList<>();
        for (Dragon dragon : collectionManager.getDragonPriorityQueue()) {
            if (dragon.getAge() > age) {
                String owner = DBRouter.getDragonOwnerById(dragon.getID());
                if (owner != null && owner.equals(currentUser)) {
                    idsToRemove.add(dragon.getID());
                }
            }
        }

        for (Long id : idsToRemove) {
            if (!DBRouter.removeDragonCascadeById(id)) {
                return  new Response("Не удалось удалить элементы из бд");
            }
        }

        collectionManager.removeGreaterByOwner(age, currentUser);
        int countRemoved = countBefore - collectionManager.size();

        if (countRemoved > 0) {
            return new Response("Удалены все ваши элементы, превышающие возраст " + age);
        } else {
            return new Response("Нет ваших элементов, превышающих возраст " + age);
        }
    }

    public String descr() {
        return "remove_greater - удаляет все ваши элементы, превышающие заданный возраст";
    }
}