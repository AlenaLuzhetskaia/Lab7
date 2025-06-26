package org.example.Other;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
public class ClassReader {
    private ArrayList<String> lines = new ArrayList<>();
    private static final Scanner scanner = new Scanner(System.in);
    private FileReader fileReader;
    private int counter = -1;
    private String name;

    public static String consoleReadLine() {
        return scanner.nextLine();
    }

    public ArrayList<String> readLines() {
        try {
            StringBuilder line = new StringBuilder();
            int character;

            while ((character = fileReader.read()) != -1) {
                char ch = (char) character;

                if (ch == '\n') {
                    lines.add(line.toString());
                    line.setLength(0);
                } else if (ch != '\r') {
                    line.append(ch);
                }
            }
        } catch (IOException e) {
            System.out.println("Ошибка при чтении файла: " + e.getMessage());
        }
        if (lines.isEmpty()) return new ArrayList<String>();
        return lines;
    }

    public void readFile(String name) throws FileNotFoundException {
        try {
            fileReader = new FileReader(System.getProperty("user.dir")
                    + "/" + name);
            this.readLines();
        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден :(");
        }
        this.name = name;
    }

    public String readLine() {
        counter++;
        if (counter < lines.size()) {
            return lines.get(counter);
        } return null;
    }

    public void skipLine() throws IOException {
        this.readLine();
    }


    public String XMLReadLine() {
        String line = this.readLine();
        if (line == null) return null;
        int startIndex = line.indexOf('>') + 1;
        int endIndex = line.lastIndexOf('<');
        return line.substring(startIndex, endIndex).trim();
    }

}
