package sda.group3.bravenewwords;

import java.sql.*;
import java.util.Arrays;

public class Database {
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/she_goes_tech", "root", "password");
    }

    public static void createTable(Connection connection) throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS result(" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "player_name VARCHAR(200)," +
                "story TEXT)";

        try (Statement statement = connection.createStatement()) {
            statement.execute(sql);
        }
    }

    public static void insertIntoTable(Connection connection, String playerName, String[] story) throws SQLException {
       String storyToString = Arrays.toString(story);
        String sql = "INSERT INTO result (player_name,story) VALUES(?,?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, playerName);
            statement.setString(2, storyToString);
            statement.execute();

        }catch (Exception e){
            System.out.println(e.getCause());
        }
    }

    // finds if specific word is in any story
    public static void printDatabaseRecord(Connection connection, String searchWord) throws SQLException {
        String sql = "SELECT * from result WHERE story LIKE '%" + searchWord.toUpperCase() + "%'";
        int count = 0;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String story = resultSet.getString("story").replaceAll("[\\[,\\]]", "");
                System.out.println(id + " | " + story);
                count++;
            }
            if (count == 0){
                System.out.println("Auch! Bad choice! No stories include word " + searchWord);
            }
        }
    }

    // compares whether players answer is in the whitelist
    public static boolean compareToWhitelist(Connection connection, String answer) throws SQLException{
        String sql = "SELECT * FROM whitelist WHERE forbidden_word='" + answer + "'";
        boolean wordIsInTheWhitelist;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery(sql);
            wordIsInTheWhitelist = resultSet.next();
            }
//        catch (Exception e) {
//        System.out.println(e.getCause());
//        }
        return wordIsInTheWhitelist;
    }
}
