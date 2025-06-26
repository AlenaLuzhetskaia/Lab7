package org.example.Command;
import org.example.Other.*;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class LoginCommand implements Command {
    @Override
    public Response execute(Request request) throws IOException {
        try {
            if (DBRouter.checkUser(request.parameter().split(" ")[0], request.parameter().split(" ")[1])){
//                Session.setCurrentUserLogin(request.parameter().split(" ")[0]);
                return new Response("Вы успешно вошли в аккаунт!");
            }
        } catch (SQLException | NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        return new Response("Неверное имя пользователя или пароль");
    }

    @Override
    public String descr() {
        return "login - войти в аккаунт";
    }
}
