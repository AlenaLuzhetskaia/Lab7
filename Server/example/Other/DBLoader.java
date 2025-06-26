package org.example.Other;

import org.example.IDGenerator;
import org.example.Command.CollectionManager;
import org.example.Option.*;

import java.sql.*;
import java.util.Date;

public class DBLoader {

    private static final Connection conn = DBConnection.ConnectDB();

    public static void loadDragonsFromDB(CollectionManager collectionManager) {
        String sql = """
            SELECT 
                d.id, d.name, d.creation_date, d.age, d.wingspan, d.color, d.character,
                coord.x, coord.y,
                p.name AS killer_name, p.weight AS killer_weight, p.passport_id AS killer_passport_id, p.eye_color AS killer_eye_color
            FROM Dragon d
            JOIN Coordinates coord ON d.coordinates_id = coord.id
            LEFT JOIN Person p ON d.killer_id = p.id
        """;

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                long id = rs.getLong("id");
                String name = rs.getString("name");
                Timestamp creationTimestamp = rs.getTimestamp("creation_date");
                Date creationDate = creationTimestamp != null ? new Date(creationTimestamp.getTime()) : new Date();
                int age = rs.getInt("age");
                int wingspan = rs.getInt("wingspan");
                Color color = Color.valueOf(rs.getString("color"));
                String characterStr = rs.getString("character");
                DragonCharacter character = characterStr != null ? DragonCharacter.valueOf(characterStr) : null;

                Double x = rs.getDouble("x");
                int y = rs.getInt("y");
                Coordinates coordinates = new Coordinates(x, y);

                Person killer = null;
                String killerName = rs.getString("killer_name");
                if (killerName != null) {
                    double killerWeight = rs.getDouble("killer_weight");
                    String killerPassportID = rs.getString("killer_passport_id");
                    Color killerEyeColor = Color.valueOf(rs.getString("killer_eye_color"));
                    killer = new Person(killerName, killerWeight, killerPassportID, killerEyeColor);
                }

                Dragon dragon = new Dragon(
                        id, name, coordinates, creationDate, age, wingspan, color, character, killer
                );
                IDGenerator.setFirstID(dragon.getId());
                collectionManager.add(dragon);
            }
            System.out.println("успешно загружено из базы данных");
        } catch (SQLException e) {
            System.err.println("Ошибка при загрузке из БД: " + e.getMessage());
            e.printStackTrace();
        }
    }
}