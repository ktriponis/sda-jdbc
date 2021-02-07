package sda.jdbc;

import java.sql.*;

public class PirmaUzduotis {

    private static final String URL = "jdbc:h2:mem:theater";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    public static void main(String[] args) throws ClassNotFoundException {
        Class.forName("org.h2.Driver");
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {

            MovieDAO movieDAO = new MovieDAOImpl(conn);
            movieDAO.deleteTable();
            movieDAO.createTable();

            createMovie("Interstellar", conn);
            createMovie("100", conn);

            System.out.println("Pries:");
            printMovies(conn);

            try (PreparedStatement updateMovie = conn.prepareStatement("UPDATE MOVIES SET title = ? WHERE id = ?")) {
                updateMovie.setString(1, "A.I.");
                updateMovie.setInt(2, 1);
                updateMovie.execute();
            }

            System.out.println("Po:");
            printMovies(conn);

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    private static void printMovies(Connection conn) throws SQLException {
        try (Statement selectMovies = conn.createStatement()) {
            ResultSet resultSet = selectMovies.executeQuery("SELECT * FROM MOVIES");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                System.out.println("id: " + id + "; title: " + title);
            }
        }
    }

    private static void createMovie(String title, Connection conn) {
        try (Statement insertMovie = conn.createStatement()) {
            insertMovie.execute("INSERT INTO MOVIES (title) VALUES('" + title + "')");
        } catch (SQLException e) {
            System.out.println("Nepavyko prideti filmo");
        }
    }
}
