package org.example.Other;

import org.example.Option.Dragon;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public record Response(String message, List<Dragon> dragons) implements Serializable {
    public Response(String message) {
        this(message, Collections.emptyList());
    }
    public Response(Dragon... dragons) {
        this(null, Arrays.asList(dragons));
    }
    public Response(List<Dragon> dragons) {
        this(null, dragons);
    }
}
