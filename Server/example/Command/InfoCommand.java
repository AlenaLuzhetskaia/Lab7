package org.example.Command;

import org.example.Other.Request;
import org.example.Other.Response;

public class InfoCommand implements Command {
    CollectionManager collectionManager;

    public InfoCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    public Response execute(Request request) {
        return new Response("Размер коллекции: " + collectionManager.size() + "\n"
            + "Дата инициализации: " + collectionManager.creationDate().toString());
    }

    public String descr() {
        return "info - информация о коллекции";
    }
}
