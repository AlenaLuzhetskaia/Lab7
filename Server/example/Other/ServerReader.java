package org.example.Other;

import java.util.Scanner;

public class ServerReader {
    static Scanner scanner = new Scanner(System.in);

    public static String readLine(){
        return scanner.nextLine();
    }

    public static boolean hasCommand(){
        return scanner.hasNext();
    }
}
