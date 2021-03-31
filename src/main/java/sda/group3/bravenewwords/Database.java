package sda.group3.bravenewwords;

import java.sql.*;
import java.util.Arrays;

public class Database {
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/she_goes_tech", "root", "password");
    }

    public static PreparedStatement createPreparedStatement() throws SQLException {
        Connection connection = getConnection();
        return connection.prepareStatement("");
    }



    public static void createTable(Connection connection) throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS result(" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "player_name VARCHAR(200)," +
                "story TEXT)";

        //Try with resources
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

//        }catch (Exception e){
//            System.out.println(e.getCause());
//        }
    }}


    public static void printAllDatabaseRecord(Connection connection) throws SQLException {
        String sql = "SELECT * from student";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("first_name");
                String address = resultSet.getString("address");
                String program = resultSet.getString("program");

                System.out.println(id + " | " + name + " | " + address + " | " + program);
            }
        }
    }

}
