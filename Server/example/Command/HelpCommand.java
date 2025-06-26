package org.example.Command;

import org.example.Other.Request;
import org.example.Other.Response;

import java.util.Map;

public class HelpCommand implements Command {
    CollectionManager collectionManager;
    Map<String, Command> commands;

    public HelpCommand(CollectionManager collectionManager, Map<String, Command> commands) {
        this.collectionManager = collectionManager;
        this.commands = commands;
    }

    public Response execute(Request request) {
        String help = "";
        for (Command command : commands.values()) {
            help = help + command.descr() + "\n";
        }
        return new Response(help);
    }

    public String descr() {
        return "help - помощь";
    }
}
