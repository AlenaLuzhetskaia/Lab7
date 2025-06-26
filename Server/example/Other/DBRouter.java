package org.example.Other;

import org.example.Option.*;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

public class DBRouter {
    private static Connection conn = DBConnection.ConnectDB();

    // Добавление нового пользователя
    public static boolean addNewUser(String name, String pass) throws SQLException {
        if (isUserExists(name)) return false;
        String sql = "INSERT INTO MyUsers (name, password, created_at) VALUES (?, ?, CURRENT_TIMESTAMP)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, Hasher.hashPass(pass));
            pstmt.executeUpdate();
        } catch (SQLException | NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    // Проверка существования пользователя
    public static boolean isUserExists(String name) {
        String query = "SELECT 1 FROM MyUsers WHERE name = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            return false;
        }
    }

    // Проверка пользователя по паролю
    public static boolean checkUser(String name, String pass) throws SQLException, NoSuchAlgorithmException {
        if (isUserExists(name)) {
            PreparedStatement stmt = conn.prepareStatement("SELECT password FROM MyUsers WHERE name = ?");
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String userPass = rs.getString("password");
                return userPass.equals(Hasher.hashPass(pass));
            }
        }
        return false;
    }

    // Добавление нового дракона
    public static Long addDragon(Dragon dragon, String user) {
    try {
        // Сначала вставляем координаты
        String coordSql = "INSERT INTO Coordinates (x, y) VALUES (?, ?) RETURNING id";
        PreparedStatement coordStmt = conn.prepareStatement(coordSql);
        coordStmt.setDouble(1, dragon.getCoordinates().getX());
        coordStmt.setInt(2, dragon.getCoordinates().getY());
        ResultSet coordRs = coordStmt.executeQuery();
        long coordinatesId;
        if (coordRs.next()) {
            coordinatesId = coordRs.getLong("id");
        } else {
            coordRs.close();
            coordStmt.close();
            return null;
        }
        coordRs.close();
        coordStmt.close();

        Long killerId = null;
        if (dragon.getKiller() != null) {
            String killerSql = "INSERT INTO Person (name, weight, passport_id, eye_color) VALUES (?, ?, ?, ?) RETURNING id";
            PreparedStatement killerStmt = conn.prepareStatement(killerSql);
            killerStmt.setString(1, dragon.getKiller().getName());
            killerStmt.setDouble(2, dragon.getKiller().getWeight());
            killerStmt.setString(3, dragon.getKiller().getPassportID());
            killerStmt.setString(4, dragon.getKiller().getEyeColor().toString());
            ResultSet killerRs = killerStmt.executeQuery();
            if (killerRs.next()) {
                killerId = killerRs.getLong("id");
            }
            killerRs.close();
            killerStmt.close();
        }

        String dragonSql = "INSERT INTO Dragon (name, coordinates_id, creation_date, age, wingspan, color, character, killer_id, owner) " +
                "VALUES (?, ?, CURRENT_TIMESTAMP, ?, ?, ?, ?, ?, ?) RETURNING id";
        PreparedStatement dragonStmt = conn.prepareStatement(dragonSql);
        dragonStmt.setString(1, dragon.getName());
        dragonStmt.setLong(2, coordinatesId);
        dragonStmt.setInt(3, dragon.getAge());
        dragonStmt.setInt(4, dragon.getWingspan());
        dragonStmt.setString(5, dragon.getColor().toString());
        if (dragon.getCharacter() != null) {
            dragonStmt.setString(6, dragon.getCharacter().toString());
        } else {
            dragonStmt.setNull(6, Types.VARCHAR);
        }
        if (killerId != null) {
            dragonStmt.setLong(7, killerId);
        } else {
            dragonStmt.setNull(7, Types.BIGINT);
        }
        dragonStmt.setString(8, user);

        ResultSet dragonRs = dragonStmt.executeQuery();
        Long dragonId = null;
        if (dragonRs.next()) {
            dragonId = dragonRs.getLong("id");
        }
        dragonRs.close();
        dragonStmt.close();
        return dragonId;
    } catch (SQLException e) {
        return null;
    }
}

    // Обновление дракона
    public static boolean updateDragon(long id, Dragon dragon) {
        try {
            PreparedStatement getIds = conn.prepareStatement("SELECT coordinates_id, killer_id FROM Dragon WHERE id = ?");
            getIds.setLong(1, id);
            ResultSet rs = getIds.executeQuery();
            if (!rs.next()) {
                rs.close();
                getIds.close();
                return false;
            }
            long coordinatesId = rs.getLong("coordinates_id");
            long killerId = rs.getLong("killer_id");
            rs.close();
            getIds.close();

            // Update Coordinates
            PreparedStatement updateCoordinates = conn.prepareStatement(
                    "UPDATE Coordinates SET x = ?, y = ? WHERE id = ?"
            );
            updateCoordinates.setDouble(1, dragon.getCoordinates().getX());
            updateCoordinates.setInt(2, dragon.getCoordinates().getY());
            updateCoordinates.setLong(3, coordinatesId);
            updateCoordinates.executeUpdate();
            updateCoordinates.close();

            // Update Killer (Person)
            if (dragon.getKiller() != null) {
                PreparedStatement updateKiller = conn.prepareStatement(
                        "UPDATE Person SET name = ?, weight = ?, passport_id = ?, eye_color = ? WHERE id = ?"
                );
                updateKiller.setString(1, dragon.getKiller().getName());
                updateKiller.setDouble(2, dragon.getKiller().getWeight());
                updateKiller.setString(3, dragon.getKiller().getPassportID());
                updateKiller.setString(4, dragon.getKiller().getEyeColor().toString());
                updateKiller.setLong(5, killerId);
                updateKiller.executeUpdate();
                updateKiller.close();
            }

            // Update Dragon
            PreparedStatement updateDragon = conn.prepareStatement(
                    "UPDATE Dragon SET name = ?, age = ?, wingspan = ?, color = ?, character = ? WHERE id = ?"
            );
            updateDragon.setString(1, dragon.getName());
            updateDragon.setInt(2, dragon.getAge());
            updateDragon.setInt(3, dragon.getWingspan());
            updateDragon.setString(4, dragon.getColor().toString());
            if (dragon.getCharacter() != null) {
                updateDragon.setString(5, dragon.getCharacter().toString());
            } else {
                updateDragon.setNull(5, Types.VARCHAR);
            }
            updateDragon.setLong(6, id);
            int rows = updateDragon.executeUpdate();
            updateDragon.close();

            return rows > 0;
        } catch (Exception e) {
            return false;
        }
    }

    // Удаление дракона и связанных сущностей
    public static boolean removeDragonCascadeById(long id) {
        try {
            PreparedStatement getIds = conn.prepareStatement("SELECT coordinates_id, killer_id FROM Dragon WHERE id = ?");
            getIds.setLong(1, id);
            ResultSet rs = getIds.executeQuery();
            if (!rs.next()) {
                rs.close();
                getIds.close();
                return false;
            }
            long coordinatesId = rs.getLong("coordinates_id");
            long killerId = rs.getLong("killer_id");
            rs.close();
            getIds.close();

            PreparedStatement deleteDragon = conn.prepareStatement("DELETE FROM Dragon WHERE id = ?");
            deleteDragon.setLong(1, id);
            deleteDragon.executeUpdate();
            deleteDragon.close();

            PreparedStatement deleteCoordinates = conn.prepareStatement("DELETE FROM Coordinates WHERE id = ?");
            deleteCoordinates.setLong(1, coordinatesId);
            deleteCoordinates.executeUpdate();
            deleteCoordinates.close();

            if (killerId != 0) {
                PreparedStatement deleteKiller = conn.prepareStatement("DELETE FROM Person WHERE id = ?");
                deleteKiller.setLong(1, killerId);
                deleteKiller.executeUpdate();
                deleteKiller.close();
            }

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Получить владельца дракона по id
    public static String getDragonOwnerById(long id) {
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT owner FROM Dragon WHERE id = ?");
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            String owner = null;
            if (rs.next()) {
                owner = rs.getString("owner");
            }
            rs.close();
            stmt.close();
            return owner;
        } catch (Exception e) {
            return null;
        }
    }

    // Очистить всех драконов пользователя
    public static boolean clearDragonsByOwner(String owner) {
        try {
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM Dragon WHERE owner = ?");
            stmt.setString(1, owner);
            stmt.executeUpdate();
            stmt.close();
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}