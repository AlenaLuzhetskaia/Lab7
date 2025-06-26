package org.example.Command;

import org.example.Option.Dragon;
import org.example.Other.DBRouter;
import org.example.Other.Request;
import org.example.Other.Response;

public class UpdateCommand implements Command {
    private final CollectionManager collectionManager;

    public UpdateCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    public Response execute(Request request) {
        long id;
        try {
            id = Long.parseLong(request.parameter());
        } catch (Exception e) {
            return new Response("Введите id корректно");
        }
        Dragon dragonToUpdate = collectionManager.getById(id);
        if (dragonToUpdate == null) {
            return new Response("Элемент с id " + id + " не найден");
        }

        String currentUser = request.userLogin();
        String owner = org.example.Other.DBRouter.getDragonOwnerById(id);
        if (owner == null) {
            return new Response("молодой человек вы кто?");
        }
        if (!owner.equals(currentUser)) {
            return new Response("Вы не являетесь владельцем этого элемента");
        }
        boolean updatedInDb = DBRouter.updateDragon(id, request.dragon());
        if (!updatedInDb) {
            return new Response("Ошибка при обновлении элемента в базе данных");
        }
        collectionManager.updateById(id, request.dragon());

        return new Response("Элемент с id " + id + " успешно обновлён!");
    }

    public String descr() {
        return "update - обновить информацию о вашем элементе по id";
    }
}