package org.example.Other;

import org.example.Option.Dragon;

import java.io.Serializable;

public record Request(String command, String parameter, Dragon dragon, String userLogin) implements Serializable {
    public Request(String command, String parameter) {
        this(command, parameter, null, null);
    }
    public Request(String command, String parameter, Dragon dragon){
        this(command, parameter, dragon, null);
    }

    public String getParameter() {
        return this.parameter;
    }
}