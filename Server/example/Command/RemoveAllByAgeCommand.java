package org.example.Command;

import org.example.Other.DBRouter;
import org.example.Other.Request;
import org.example.Other.Response;
import org.example.Option.Dragon;

public class RemoveAllByAgeCommand implements Command {
    private final CollectionManager collectionManager;

    public RemoveAllByAgeCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    public Response execute(Request request) {
        int age;
        try {
            age = Integer.parseInt(request.parameter());
        } catch (NumberFormatException e) {
            return new Response("Введите числовое значение");
        }

        String currentUser = request.userLogin();
        boolean found = false;
        for (Dragon dragon : collectionManager.getDragonPriorityQueue()) {
            if (dragon.getAge() == age) {
                String owner = DBRouter.getDragonOwnerById(dragon.getID());
                if (owner != null && owner.equals(currentUser)) {
                    if (DBRouter.removeDragonCascadeById(dragon.getID())) {
                        found = true;
                    }
                }
            }
        }
        collectionManager.removeAllByAgeAndOwner(age, currentUser);

        if (found) {
            return new Response("Удалены все ваши элементы с возрастом: " + age);
        } else {
            return new Response("Нет элементов с возрастом: " + age + " для удаления");
        }
    }

    public String descr() {
        return "remove_all_by_age - удаляет все ваши элементы, где значение поля age эквивалентно заданному";
    }
}