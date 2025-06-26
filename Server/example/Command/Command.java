package org.example.Command;

import org.example.Other.Request;
import org.example.Other.Response;

import java.io.IOException;
import java.text.ParseException;

public interface Command {
    Response execute(Request request) throws IOException, ParseException;
    String descr();
}
