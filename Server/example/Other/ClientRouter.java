package org.example.Other;

import java.io.*;
import java.net.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class ClientRouter {
    public static void connectionStart() throws IOException {
        ServerSocket socket = new ServerSocket(1337);
        ClientHandler.start();

        // Параллельное чтение консоли
        new Thread(() -> {
            while (true) {
                String line = ServerReader.readLine();
                if ("save".equals(line)) {
                    try {
                        ClientHandler.save();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }).start();

        ExecutorService clientAcceptPool = Executors.newCachedThreadPool();

        while (true) {
            Socket client = socket.accept();
            System.out.println(client.getInetAddress());
            clientAcceptPool.execute(() -> {
                Session session = new Session(); // индивидуально для клиента
                try {
                    ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
                    out.flush();
                    ObjectInputStream in = new ObjectInputStream(client.getInputStream());
                    AtomicBoolean isLogin = new AtomicBoolean(false);
                    while (true) {
                        try {
                            Request request = (Request) in.readObject();
                            Request finalRequest =  new Request(request.command(), request.parameter(), request.dragon(), session.getUserLogin());
                            System.out.println("Получен запрос: " + finalRequest);
                            new Thread(() -> {
                                Response response = null;
                                try {
                                    response = ClientHandler.executionCommand(finalRequest, isLogin.get());
                                } catch (IOException e) {
                                    System.out.println(e.getMessage());
                                }
                                System.out.println("Отправляем ответ: " + response);
                                Response finalResponse = response;
                                // Отправка ответа через ForkJoinPool
                                ForkJoinPool.commonPool().execute(() -> {
                                    try {
                                        out.writeObject(finalResponse);
                                        out.flush();
                                    } catch (IOException e) {
                                        System.out.println("Ошибка при отправке ответа: " + e.getMessage());
                                    }
                                });

                                if (!isLogin.get() && ("Вы успешно вошли в аккаунт!".equals(response.message())
                                        || "Регистрация прошла успешно!".equals(response.message()))) {
                                    isLogin.set(true);
                                    // Сохраняем логин пользователя в сессию
                                    session.setUserLogin(finalRequest.parameter().split(" ")[0]);
                                }
                            }).start();

                        } catch (ClassNotFoundException e) {
                            System.out.println("Неверный формат ответа от сервера");
                        } catch (EOFException | SocketException e) {
                            break;
                        } catch (Exception e) {
                            System.out.println("Произошла ошибка при обработке запроса " + e);
                        }
                    }
                    in.close();
                    out.close();
                    client.close();
                } catch (IOException e) {
                    System.out.println("Произошла ошибка при получении запроса");
                }
            });
        }
    }
}