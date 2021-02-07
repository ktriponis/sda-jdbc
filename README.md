# H2 pakeitimas į MySQL

Atlikti šiuos pakeitimus `src/sda/db/jdbc/Main.java` faile

1. 9 eilutę pakeisti iš `org.h2.Driver` į `com.mysql.cj.jdbc.Driver`
2. 10 eilutę pakeisti iš `jdbc:h2:mem:theater` į `jdbc:mysql://localhost/theater` kur `theater` yra jūsų duombazės pavadinimas
3. 11 ir 12 eilutes pakeisti į MySQL prisijungimus (default user: `root`, password tuščias)

po šių pakeitimų prisijungimas turėtu būti vykdomas į MySQL duomenų bazę
