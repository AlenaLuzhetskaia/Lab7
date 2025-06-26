package org.example.Other;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.SocketChannel;
import java.util.Optional;

public class ClientNetwork {
    public static void start() {
        try {
            SocketChannel clientSocket = SocketChannel.open(new InetSocketAddress("localhost", 1337));
            ObjectOutputStream out = new ObjectOutputStream(clientSocket.socket().getOutputStream());
            out.flush();

            ObjectInputStream in = new ObjectInputStream(clientSocket.socket().getInputStream());
            System.out.println("Соединение прошло успешно!");
            while (true) {
                String text = ClassReader.consoleReadLine();
                if (text.isBlank()) continue;
                Response response = push(Parser.parse(text), clientSocket, in, out);
                Optional.ofNullable(response.message())
                        .filter(s -> !s.isBlank())
                        .ifPresent(System.out::println);
                Optional.ofNullable(response.dragons())
                        .filter(l -> !l.isEmpty())
                        .ifPresent(l -> l.forEach(System.out::println));
            }
        } catch (IOException e) {
            System.out.println("Сервер временно недоступен");;
        }
    }
    public static Response push(Request request, SocketChannel clientSocket, ObjectInputStream in , ObjectOutputStream out) throws IOException{
        try {
            if ("execute_script".equals(request.command())) {
                return ScriptReader.readScript(request.parameter(), clientSocket, in, out);
            }
            if ("exit".equals(request.command())) {
                System.out.println("выход");
                clientSocket.close();
            }
            out.writeObject(request);
            out.flush();
            Response response = (Response) in.readObject();
            System.out.println(response);
            return response;
        } catch (EOFException e) {
            throw new IOException("Соединение  разорвано");
        } catch (ClassNotFoundException e) {
            throw new IOException("Неверный формат ответа от сервера");
        }

    }
}
