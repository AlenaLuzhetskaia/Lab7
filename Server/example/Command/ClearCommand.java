package org.example.Command;
import org.example.Option.Dragon;
import org.example.Other.DBRouter;
import org.example.Other.Request;
import org.example.Other.Response;
import java.util.ArrayList;
import java.util.List;

public class ClearCommand implements Command {
    private final CollectionManager collectionManager;

    public ClearCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    public Response execute(Request request) {
        String currentUser = request.userLogin();
        List<Long> idsToRemove = new ArrayList<>();
        for (Dragon dragon : collectionManager.getAll()) {
            String dragonOwner = org.example.Other.DBRouter.getDragonOwnerById(dragon.getID());
            if (currentUser.equals(dragonOwner)) {
                idsToRemove.add(dragon.getID());
            }
        }
        boolean dbCleared = DBRouter.clearDragonsByOwner(currentUser);
        if (!dbCleared) {
            return new Response("Ошибка при удалении элементов из базы данных");
        }
        for (Long id : idsToRemove) {
            collectionManager.removeByID(id);
        }

        return new Response("Все элементы успешно удалены!");
    }

    public String descr() {
        return "clear - удалить все ваши элементы из коллекции";
    }
}