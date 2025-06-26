package org.example.Other;

import org.example.Option.Dragon;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.channels.SocketChannel;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class ScriptReader {
    static Set<String> fileName = new HashSet<>();
    public static Response readScript(String name, SocketChannel clientSocket, ObjectInputStream in, ObjectOutputStream out) throws IOException, ClassNotFoundException {
        ClassReader classReader = new ClassReader();
        String message = "";
        classReader.readFile(name);
        if (fileName.contains(classReader.getFileName().trim())) {
            return null;
        } else {
            fileName.add(classReader.getFileName().trim());
            while (true) {
                String line = classReader.readLine();
                if (line == null) {
                    break;
                }
                Request request = Parser.parse(line, classReader);
                Response response = ClientNetwork.push(request, clientSocket, in, out);
                if (response == null) {
                    message += "Отменено выполнение скрипта из-за рекурсии \n";
                } else {
                    if ((!Objects.equals(response.message(), "")) && (!Objects.equals(response.message(), "null"))) {
                        message = message + response.message() + "\n";
                    }
                    if (!response.dragons().isEmpty()) {
                        for (Dragon i : response.dragons()) {
                            message = message + i.toString() + "\n";
                        }
                    }
                }}
            fileName.remove(name);
        }
        return new Response(message + "\n" + "Скрипт выполнен\n");
    }
}
