package org.example.Command;

import org.example.Other.DBRouter;
import org.example.Other.Request;
import org.example.Other.Response;

public class AddCommand implements Command {
    private final CollectionManager collectionManager;

    public AddCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    public Response execute(Request request) {

//        request.dragon().setId(IDGenerator.generateID());
        Long id = DBRouter.addDragon(request.dragon(), request.userLogin());
        if (id != null) {
            request.dragon().setId(id);
            collectionManager.add(request.dragon());
            return new Response("Элемент успешно добавлен!");
        }
        return new Response("Ошибка при добавлении в бд");
    }

    public String descr() {
        return "add - добавление нового элемента";
    }
}
