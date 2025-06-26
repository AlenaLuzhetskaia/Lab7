package org.example.Command;

import org.example.Other.DBRouter;
import org.example.Other.Request;
import org.example.Other.Response;


import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

public class RegCommand implements Command {
    @Override
    public Response execute(Request request) throws IOException {
        if (Objects.equals(request.parameter(), "")){
            return new Response("Нельзя создать пользователя с пустым именем");
        }
        String login = request.parameter().split(" ")[0];
        String password = request.parameter().split(" ")[1];
        try {
            if (DBRouter.addNewUser(login, password)){
//                Session.setCurrentUserLogin(login);
                return new Response("Регистрация прошла успешно!");
            }
        } catch (SQLException e) {
            return new Response("Произошла ошибка с бд");
        }
        return new Response("Пользователь с таким именем уже существует");
    }

    @Override
    public String descr() {
        return "Регистрация нового пользователя";
    }
}