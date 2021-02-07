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

            movieDAO.createMovie(new Movie("Interstellar", "Sci-fi/", 2014));
            movieDAO.createMovie(new Movie("300", "Action", 2007));
            movieDAO.createMovie(new Movie("Tony", "Horror", 1986));

            movieDAO.deleteMovie(2);

            movieDAO.updateMoviesTitle(3, "It");

            System.out.print("Paieskos Rezultatas: ");
            System.out.println(movieDAO.findMovieById(1)
                    .map(Object::toString)
                    .orElse("Nerasta"));

            System.out.println("Visi Filmai: ");
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
}
