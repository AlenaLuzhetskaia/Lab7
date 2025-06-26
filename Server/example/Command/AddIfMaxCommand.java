package org.example.Command;

import org.example.Filler;
import org.example.Option.Dragon;
import org.example.Other.DBRouter;
import org.example.Other.Request;
import org.example.Other.Response;

public class AddIfMaxCommand implements Command {
    private final CollectionManager collectionManager;

    public AddIfMaxCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    public Response execute(Request request) {
        if (collectionManager.isMax(request.dragon())) {
            Long id = DBRouter.addDragon(request.dragon(), request.userLogin());
            if (id != null) {
                request.dragon().setId(id);
                collectionManager.add(request.dragon());
                return new Response("Элемент успешно добавлен!");
            }
            return new Response("Ошибка при добавлении в бд");
        }
        return new Response("Элемент не добавлен, так как значение поля age не является максимальным");

    }

    public String descr() {
        return "add_if_max - добавляет новый элемент в коллекцию, если его значение поля age превышает значение наибольшего элемента этой коллекции";
    }
}
