package org.example.Command;

import org.example.Option.Dragon;
import org.example.Other.ClassReader;
import org.example.Other.Request;
import org.example.Other.Response;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLOutput;
import java.text.ParseException;
import java.util.HashSet;
import java.util.Objects;

public class ExecuteScriptCommand implements Command {
    private final CollectionManager collectionManager;
    HashSet<String> fileNames = new HashSet<>();

    public ExecuteScriptCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    public Response execute(Request request) throws IOException, ParseException {
//        Invoker invoker = new Invoker();
//        invoker.start();
//        ClassReader reader = new ClassReader();
//        try {
//            reader.readFile(parameter);
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//        if (fileNames.contains(reader.getName())) {
//            System.out.println("Выполнение скрипта " + reader.getName() + " отменено");
//            return;
//        }
//        fileNames.add(reader.getName());
//        for (var i = 0; i < reader.readLines().size(); i++) {
//            String command = reader.readLine();
//            System.out.println(command);
//            if (command == null) {
//                break;
//            }
//            if (Objects.equals(command, "add")) {
//                Filler filler = new Filler();
//                Dragon dragon = filler.fill(reader);
//                if (dragon == null) continue;
//                collectionManager.add(dragon);
//            }
//            else if (Objects.equals(command, "update")) {
//                Dragon oldDragon = collectionManager.getById(Long.parseLong(reader.readLine()));
//                Filler filler = new Filler();
//                Dragon dragon = filler.fill(reader);
//                if (dragon == null) continue;
//            } else {
//                invoker.executionCommand(command);
//            }
//        }
//        fileNames.remove(reader.getName());
        return null;
    }

    public String descr() {
        return "execute_script - считывает скрипт из указанного файла";
    }
}
