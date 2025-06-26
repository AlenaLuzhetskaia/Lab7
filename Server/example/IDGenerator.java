package org.example;

public class IDGenerator {
    static long id = 0;
    public static long generateID() {
        id++;
        return id;
    }
    public static void setFirstID(long firstID) {
        id = firstID;
    }
}
