package org.example.db;

import java.nio.file.*;
import java.sql.Connection;
import java.sql.Statement;

public class DatabaseInitializer {

    public static void init() {
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            String sql = new String(Files.readAllBytes(Paths.get("src/main/resources/init.sql")));
            stmt.execute(sql);

        } catch (Exception e) {
            System.out.println("Error initializing database: " + e.getMessage());
        }
    }
}
