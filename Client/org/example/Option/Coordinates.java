package org.example.Option;

import java.io.Serializable;

public class Coordinates implements Serializable {
    private Double x; //Поле не может быть null
    private int y;

    public Coordinates(Double x, int y) {
        setX(x);
        this.y = y;
    }

    public void setX(Double x) {
        if (x == null) {
            throw new IllegalArgumentException("Координата x не может быть null");
        }
        this.x = x;
    }

    public Double getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String toString() {
        return "\n\tx: " + x + "\n\t" + "y: " + y;
    }

    public String toXML() {
        return "\t\t\t<x>" + this.x + "</x>\n\t\t\t<y>" + this.y + "</y>\n";
    }
}
