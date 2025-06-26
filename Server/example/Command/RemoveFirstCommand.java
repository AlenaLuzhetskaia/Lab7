package org.example.Command;

import org.example.Option.Dragon;
import org.example.Other.DBRouter;
import org.example.Other.Request;
import org.example.Other.Response;
import org.example.Other.Session;

public class RemoveFirstCommand implements Command {
    private final CollectionManager collectionManager;

    public RemoveFirstCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    public Response execute(Request request) {
        if (collectionManager.getDragonPriorityQueue().isEmpty()) {
            return new Response("Коллекция пуста");
        }

        Dragon firstDragon = collectionManager.getDragonPriorityQueue().peek();
        if (firstDragon == null) {
            return new Response("Коллекция пуста");
        }

        String currentUser = request.userLogin();
        String owner = DBRouter.getDragonOwnerById(firstDragon.getID());
        if (owner == null) {
            return new Response("Элемент не найден в базе данных");
        }
        if (!owner.equals(currentUser)) {
            return new Response("Вы не являетесь владельцем первого элемента, удаление невозможно");
        }

        if (DBRouter.removeDragonCascadeById(firstDragon.getID())) {
            collectionManager.getDragonPriorityQueue().poll();
            return new Response("Первый элемент коллекции успешно удалён!");
        } else {
            return new Response("Ошибка при удалении элемента из базы данных");
        }
    }

    public String descr() {
        return "remove_first - удаляет первый элемент коллекции";
    }
}
