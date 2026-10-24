# First-Java-CRUD - Movie Watchlist

**Author:** Helin Gulec (Student ID: 79879)
**GitHub:** [helingulec79879](https://github.com/helingulec79879)

A simple CRUD (Create, Read, Update, Delete) web application written in Java.
It helps to keep a personal movie watchlist. The project is based on the Spring guide
[Creating CRUD UI with Vaadin](https://spring.io/guides/gs/crud-with-vaadin/).

## Features

- **Create** - add a movie with the *Add movie* button
- **Read** - list all movies in a table and search them by title or director
- **Update** - click a movie in the table, change its data (e.g. rating or "watched") and press *Save*
- **Delete** - click a movie in the table and press *Remove*
- Statistics: number of all movies and watched movies
- Validation: title and director are required, rating must be between 1 and 10
- Sample movies are loaded automatically at startup

## Technologies

- Java 21
- Spring Boot 4 (Spring Data JPA)
- Vaadin 25 (web user interface written in Java)
- H2 in-memory database
- Maven (Maven Wrapper included)

## Project structure

```
src/main/java/com/example/moviewatchlist/
|-- MovieWatchlistApplication.java   # entry point, loads sample data
|-- Movie.java                       # JPA entity (title, director, genre, year, rating, watched)
|-- MovieRepository.java             # Spring Data repository with search queries
|-- MainView.java                    # main page: search field, table, statistics
`-- MovieEditor.java                 # form for adding, editing and removing a movie
src/main/resources/application.properties
src/test/java/...                    # tests
doc/                                 # screenshots documenting the work
```

## How to run locally

Requirements: **JDK 21** (or newer).

Windows:

```
mvnw.cmd spring-boot:run
```

macOS / Linux:

```
./mvnw spring-boot:run
```

When the console shows `Started MovieWatchlistApplication`, open
<http://localhost:8080> in the browser.

## Documentation

Screenshots are stored in the [`doc`](doc) directory.

## Contributors

- helingulec79879 - owner of the repository
- Fatih Alkan (79731) - contributed via Pull Request (documentation improvement)
