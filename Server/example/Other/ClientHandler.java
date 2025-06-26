package org.example.Other;

import org.example.Command.CollectionManager;
import org.example.Command.*;
import org.example.CountByCharacterCommand;
import org.example.Option.Dragon;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class ClientHandler {
    private static final Map<String, Command> commands = new HashMap<>();
    private static final Map<String, Command> regCommands = new HashMap<>();
    private static CollectionManager collectionManager;
    public static Response start() throws IOException {
        CollectionManager cm = new CollectionManager();
        collectionManager = cm;
        commands.put("help", new HelpCommand(collectionManager, commands));
        commands.put("info", new InfoCommand(collectionManager));
        commands.put("show", new ShowCommand(collectionManager));
        commands.put("add", new AddCommand(collectionManager));
        commands.put("update", new UpdateCommand(collectionManager));
        commands.put("remove_by_id", new RemoveByIDCommand(collectionManager));
        commands.put("clear", new ClearCommand(collectionManager));
//        commands.put("save", new SaveCommand(collectionManager));
//        commands.put("execute_script", new ExecuteScriptCommand(collectionManager));
        commands.put("exit", new ExitCommand());
        commands.put("remove_first", new RemoveFirstCommand(collectionManager));
        commands.put("add_if_max", new AddIfMaxCommand(collectionManager));
        commands.put("remove_greater", new RemoveGreaterCommand(collectionManager));
        commands.put("remove_all_by_age", new RemoveAllByAgeCommand(collectionManager));
        commands.put("remove_any_by_color", new RemoveAnyByColorCommand(collectionManager));
        commands.put("count_by_character", new CountByCharacterCommand(collectionManager));

        regCommands.put("reg", new RegCommand());
        regCommands.put("login", new LoginCommand());

        DBConnection.ConnectDB();
        DBLoader.loadDragonsFromDB(collectionManager);
        return new Response("сервер запущен");
    }

    public static Response executionCommand(Request request, boolean isLogin) throws IOException {
        if (!isLogin) {
            try {
                Command command = regCommands.get(request.command());
                return command.execute(request);
            } catch (Exception e) {
                return new Response("Неизвестная команда\nНапишите reg (login) (password) -для регистрации\nlogin (login) (password) - для входа в аккаунт");
            }
        } else {
            try {
                Command command = commands.get(request.command());
                return (command.execute(request));
            } catch (NullPointerException e) {
                return new Response("Неизвестная команда" + " " + request.command());
            } catch (IOException e) {
                return new Response("Невозможно выполнить команду" + " " + request.command());
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public static Response save() throws IOException {
        SaveCommand saveCommand = new SaveCommand(collectionManager);
        return saveCommand.execute(new Request("", ""));
    }
//    public void fill() throws IOException, ParseException {
//        PriorityQueue<Dragon> priorityQueue = XMLConvent.XMLRead();
//        for (int i = 0; i <= priorityQueue.size(); i++) {
//            collectionManager.add(priorityQueue.poll());
//        }
//    }
}