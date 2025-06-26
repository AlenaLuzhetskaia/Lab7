package org.example.Command;

import org.example.Option.Dragon;
import org.example.Other.Request;
import org.example.Other.Response;

import java.util.PriorityQueue;

public class ShowCommand implements Command {
    CollectionManager collectionManager;

    public ShowCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    public Response execute(Request request) {
        PriorityQueue<Dragon> dragonPriorityQueue = collectionManager.getDragonPriorityQueue();
        if (collectionManager.size() == 0) {
            return new Response("Список пуст");
        } else {
            StringBuilder result = new StringBuilder("Элементы коллекции:\n");
            for (Dragon dragon : dragonPriorityQueue) {
                result.append(dragon.toString()).append("\n");
            }
            return new Response(result.toString());
        }
    }

    public String descr() {
        return "show - показать все элементы коллекции";
    }
}
