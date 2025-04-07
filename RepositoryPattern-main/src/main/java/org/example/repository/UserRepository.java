package org.example.repository;

import org.example.db.DatabaseConnection;
import org.example.entities.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserRepository implements EntityRepository<User> {

    @Override
    public void save(User user) {
        String sql = "INSERT INTO users (uuid, name, email, password) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getUuid().toString());
            stmt.setString(2, user.getName());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getPassword());

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error saving user: " + e.getMessage());
        }
    }

    @Override
    public Optional<User> findById(UUID uuid) {
        String sql = "SELECT * FROM users WHERE uuid = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, uuid.toString());

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                User user = new User(
                        UUID.fromString(rs.getString("uuid")),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("password")
                );
                return Optional.of(user);
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving user by ID: " + e.getMessage());
        }

        return Optional.empty();
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                User user = new User(
                        UUID.fromString(rs.getString("uuid")),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("password")
                );
                users.add(user);
            }

        } catch (SQLException e) {
            System.out.println("Error listing users: " + e.getMessage());
        }

        return users;
    }

    @Override
    public void deleteById(UUID uuid) {
        String sql = "DELETE FROM users WHERE uuid = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, uuid.toString());
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error deleting user: " + e.getMessage());
        }
    }
}
