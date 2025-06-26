package org.example.Command;

import org.example.Other.DBRouter;
import org.example.Other.Request;
import org.example.Other.Response;
import org.example.Other.Session;

public class RemoveByIDCommand implements Command {
    private final CollectionManager collectionManager;

    public RemoveByIDCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    public Response execute(Request request) {
        long id;
        try {
            id = Long.parseLong(request.parameter());
        } catch (NumberFormatException e) {
            return new Response("Введите число!!!");
        }

        String currentUser = request.userLogin();
        String owner = DBRouter.getDragonOwnerById(id);
        if (owner == null) {
            return new Response("Элемент с таким id не найден в базе данных");
        }
        if (!owner.equals(currentUser)) {
            return new Response("Вы не являетесь владельцем этого элемента");
        }

        if (DBRouter.removeDragonCascadeById(id)) {
            boolean isRemoved = collectionManager.removeByID(id);
            if (isRemoved) {
                return new Response("Элемент с id " + id + " успешно удалён!");
            } else {
                return new Response("Элемент с id " + id + " не найден в коллекции");
            }
        }
        return new Response("Элемента с таким id нету");
    }

    public String descr() {
        return "remove_by_id - удалить элемент по его id";
    }
}
