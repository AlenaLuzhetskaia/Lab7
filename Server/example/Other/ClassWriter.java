package org.example.Other;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;


public class ClassWriter {
    public static void writeFile(String fileName, ArrayList<ArrayList<String>> lines) throws IOException {
        try {
            FileOutputStream fos = new FileOutputStream(fileName);
            fos.write("<dragons>\n".getBytes(StandardCharsets.UTF_8));

            for (ArrayList<String> cityLines : lines) {
                for (String line : cityLines) {
                    fos.write((line + "\n").getBytes(StandardCharsets.UTF_8));
                }
            }

            fos.write("</dragons>\n".getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            System.out.println("Ошибка записи в файл: " + e.getMessage());
        }
    }
}
