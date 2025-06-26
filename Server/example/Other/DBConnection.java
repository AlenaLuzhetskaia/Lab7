package org.example.Other;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    public static Connection ConnectDB() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/studs", "s472174", "7sS5iIvWhHpEUS1S");
        } catch (Exception e) {
            System.out.println("Бд сейчас недоступно");
            System.err.println(e.getMessage());
            System.out.println(conn);
            return conn;
        }
        return conn;
    }
}
