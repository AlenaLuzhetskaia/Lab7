package org.example.Other;

import org.example.Option.Dragon;

import java.io.IOException;
import java.util.Objects;

public class Parser {
    public static Request parse(String text) throws IOException {
        String command = text.split(" ", 2)[0];
        String parameter = "";
        try {
            parameter = text.split(" ", 2)[1];
        } catch (ArrayIndexOutOfBoundsException ignore) {
        }
        if (Objects.equals(command, "add") || Objects.equals(command, "update")) {
            Filler filler = new Filler();
            Dragon dragon = filler.fill();
            return new Request(command, parameter, dragon);
        } else {
            return new Request(command, parameter);
        }
    }

    public static Request parse(String text, ClassReader classReader) throws IOException {
        String command = text.split(" ", 2)[0];
        String parameter = "";
        try {
            parameter = text.split(" ", 2)[1];
        } catch (ArrayIndexOutOfBoundsException ignore) {
        }
        if (Objects.equals(command, "add") || Objects.equals(command, "update") || Objects.equals(command, "add_if_max")) {
            Filler filler = new Filler();
            Dragon dragon = filler.fill(classReader);
            return new Request(command, parameter, dragon);
        } else {
            return new Request(command, parameter);
        }
    }
}
