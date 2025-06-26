package org.example.Command;

import org.example.Option.Color;
import org.example.Other.DBRouter;
import org.example.Other.Request;
import org.example.Other.Response;
import org.example.Option.Dragon;
import org.example.Other.Session;

public class RemoveAnyByColorCommand implements Command {
    private final CollectionManager collectionManager;

    public RemoveAnyByColorCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    public Response execute(Request request) {
        Color color;
        try {
            color = Color.valueOf(request.parameter().trim().toUpperCase());
        } catch (IllegalArgumentException | NullPointerException e) {
            return new Response("Некорректный цвет");
        }

        String currentUser = request.userLogin();
        for (Dragon dragon : collectionManager.getDragonPriorityQueue()) {
            if (dragon.getColor() == color) {
                String owner = DBRouter.getDragonOwnerById(dragon.getID());
                if (owner != null && owner.equals(currentUser)) {
                    if (DBRouter.removeDragonCascadeById(dragon.getID())) {
                        collectionManager.removeByID(dragon.getID());
                        return new Response("Элемент с цветом " + color + " успешно удалён!");
                    } else {
                        return new Response("Ошибка при удалении элемента из базы данных");
                    }
                } else {
                    return new Response("Вы не хозяин данного элемента");
                }
            }
        }
        return new Response("Элементов с цветом " + color + " не найдено");
    }

    public String descr() {
        return "remove_any_by_color (color) - удаляет один ваш элемент, значение поля color которого эквивалентно заданному";
    }
}