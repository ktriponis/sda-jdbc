# H2 pakeitimas į MySQL

Atlikti šiuos pakeitimus `src/sda/db/jdbc/Main.java` faile

1. 9 eilutę pakeisti iš `org.h2.Driver` į `com.mysql.cj.jdbc.Driver`
2. 10 eilutę pakeisti iš `jdbc:h2:mem:theater` į `jdbc:mysql://localhost/theater` kur `theater` yra jūsų duombazės pavadinimas
3. 11 ir 12 eilutes pakeisti į MySQL prisijungimus (default user: `root`, password tuščias)

po šių pakeitimų prisijungimas turėtu būti vykdomas į MySQL duomenų bazę

# Tasks

## Task 1

Using the JDBC API and any relational database (e.g. [H2](https://www.h2database.com/html/main.html)) make the following queries:

* create a table MOVIES with columns: `id` of type `INTEGER AUTO INCREMENT`,`title` of type `VARCHAR (255)`, `genre` of type `VARCHAR (255)`,`yearOfRelease` of type `INTEGER`. Note that a table named `MOVIE` may already exist. In that case, delete it.
* add any three records to the `MOVIES` table
* update **one** selected record (use the `PreparedStatement`)
* delete selected record with specified id
* display all other records in the database

In the task, focus on the correct use of the JDBC API. All queries can be made directly in the `main` method. Use a single connection to execute all queries. However, remember to use `try-with-resources` when opening a connection and creating objects such as `Statement` or `PreparedStatement`. Also, don't worry about exception handling in this task (in case of error, display stacktrace).

## Task 2

Implement the class `MovieDAOImpl` (DAO - Data Access Object). It should perform basic database operations for the `MOVIES` table, the structure of which is described in [task 1](#task-1). Assume that an object representing an open database connection comes in the constructor of this class. Remember to use `PreparedStatement` where possible and close objects (use `try-with-resources`). Also implement a `Movie` class that represents a single row in the `MOVIES` table that you will use in implementing the `MovieDAOImpl` class.

Implement the following operations. Each of them should be represented by a separate public method:

* creating a `MOVIES` table
* delete the `MOVIES` table
* adding a record
* delete record by identifier
* update of the movie title with id data
* searching for a movie by ID
* download all videos

In case of an exception `SQLException`, make the method throw an exception `DatabaseActionException`. This class should extend from the `RuntimeException` class.

The `MovieDAOImpl` class should implement the following interface:

```
import java.util.List;
import java.util.Optional;

public interface MovieDAO {
  void createTable();
  void deleteTable();

  void createMovie(final Movie movie);
  void deleteMovie(int id);
  void updateMoviesTitle(int id, String newTitle);
  Optional<Movie> findMovieById(int id);
  List<Movie> findAll();
}
```
