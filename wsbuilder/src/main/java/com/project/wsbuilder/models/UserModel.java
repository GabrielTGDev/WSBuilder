package com.project.wsbuilder.models;

import com.project.wsbuilder.dbconnection.DatabaseConnection;

import java.sql.Timestamp;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

public class UserModel {
    private int id;
    private String username;
    private String email;
    private String passwordHash;
    private String profileImage;
    private String personalInfo;
    private String description;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    private static boolean loggedIn = false;

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getPersonalInfo() {
        return personalInfo;
    }

    public void setPersonalInfo(String personalInfo) {
        this.personalInfo = personalInfo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    // Method to hash password using SHA-256
    private static String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hash = md.digest(password.getBytes());
        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            hexString.append(String.format("%02x", b));
        }
        return hexString.toString();
    }

    // Method to authenticate user
    public static boolean authenticate(String username, String password) {
        String query = "SELECT password_hash FROM users.users WHERE username = ?";
        try (DatabaseConnection dbConnection = new DatabaseConnection();
             PreparedStatement pstmt = dbConnection.getConnection().prepareStatement(query)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String storedHash = rs.getString("password_hash");
                String hashedPassword = hashPassword(password);
                loggedIn = true;
                return storedHash.equals(hashedPassword);
            }
        } catch (SQLException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Method to register user
    public static boolean register(String username, String email, String password) {
        String query = "INSERT INTO users.users (username, email, password_hash) VALUES (?, ?, ?)";
        try (DatabaseConnection dbConnection = new DatabaseConnection();
             PreparedStatement pstmt = dbConnection.getConnection().prepareStatement(query)) {
            String hashedPassword = hashPassword(password);
            pstmt.setString(1, username);
            pstmt.setString(2, email);
            pstmt.setString(3, hashedPassword);
            int rowsAffected = pstmt.executeUpdate();
            loggedIn = true;
            return rowsAffected > 0;
        } catch (SQLException e) {
            if (e.getSQLState().equals("23505")) { // Unique violation
                System.out.println("User already exists.");
            } else {
                e.printStackTrace();
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean isLoggedIn() {
        return loggedIn;
    }

    public static void logout() {
        loggedIn = false;
    }
}