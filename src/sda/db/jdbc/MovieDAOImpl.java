package sda.db.jdbc;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MovieDAOImpl implements MovieDAO {

    private final Connection connection;

    public MovieDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void createTable() {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("CREATE TABLE IF NOT EXISTS MOVIES (" +
                    "id INTEGER AUTO_INCREMENT," +
                    "title VARCHAR(255)," +
                    "genre VARCHAR(255)," +
                    "yearOfRelease INTEGER," +
                    "PRIMARY KEY (id))");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void deleteTable() {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("DROP TABLE IF EXISTS MOVIES");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void createMovie(Movie movie) {
        try (PreparedStatement stmt = connection.prepareStatement(
                "INSERT INTO MOVIES (title, genre, yearOfRelease) VALUES(?, ?, ?)"
        )) {
            stmt.setString(1, movie.getTitle());
            stmt.setString(2, movie.getGenre());
            stmt.setInt(3, movie.getYearOfRelease());
            stmt.execute();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void deleteMovie(int id) {
        try (PreparedStatement stmt = connection.prepareStatement("DELETE FROM MOVIES WHERE id = ?")) {
            stmt.setInt(1, id);
            stmt.execute();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void updateMoviesTitle(int id, String newTitle) {
        try (PreparedStatement stmt = connection.prepareStatement("UPDATE MOVIES SET title = ? WHERE id = ?")) {
            stmt.setString(1, newTitle);
            stmt.setInt(2, id);
            stmt.execute();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public Optional<Movie> findMovieById(int id) {
        try (PreparedStatement stmt = connection.prepareStatement("SELECT * FROM MOVIES WHERE id = ?")) {
            stmt.setInt(1, id);
            if (stmt.execute()) {
                ResultSet resultSet = stmt.getResultSet();
                if (resultSet.next()) {
                    return Optional.of(mapMovie(resultSet));
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public List<Movie> findAll() {
        List<Movie> movies = new ArrayList<>();
        try (Statement stmt = connection.createStatement()) {
            ResultSet resultSet = stmt.executeQuery("SELECT * FROM MOVIES");
            while (resultSet.next()) {
                movies.add(mapMovie(resultSet));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return movies;
    }

    private static Movie mapMovie(ResultSet resultSet) throws SQLException {
        return new Movie(
                resultSet.getInt("id"),
                resultSet.getString("title"),
                resultSet.getString("genre"),
                resultSet.getInt("yearOfRelease"));
    }
}
