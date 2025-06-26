package org.example.Command;

import org.example.Other.Request;
import org.example.Other.Response;

public class ExitCommand implements Command {
    public Response execute(Request request) {
        return new Response("exit");
    }

    public String descr() {
        return "exit - завершить программу";
    }
}
