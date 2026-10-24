package com.example.moviewatchlist;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Movie Watchlist - First Java CRUD application.
 *
 * @author Helin Gulec (79879)
 */
@SpringBootApplication
public class MovieWatchlistApplication {

    private static final Logger log = LoggerFactory.getLogger(MovieWatchlistApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(MovieWatchlistApplication.class, args);
    }

    @Bean
    public CommandLineRunner loadData(MovieRepository repository) {
        return (args) -> {
            // save some sample movies
            repository.save(new Movie("Inception", "Christopher Nolan", "Sci-Fi", 2010, 9, true));
            repository.save(new Movie("The Godfather", "Francis Ford Coppola", "Crime", 1972, 10, true));
            repository.save(new Movie("Spirited Away", "Hayao Miyazaki", "Animation", 2001, null, false));
            repository.save(new Movie("Parasite", "Bong Joon-ho", "Thriller", 2019, 9, true));
            repository.save(new Movie("Interstellar", "Christopher Nolan", "Sci-Fi", 2014, null, false));
            repository.save(new Movie("Amelie", "Jean-Pierre Jeunet", "Comedy", 2001, 8, true));
            repository.save(new Movie("Cold War", "Pawel Pawlikowski", "Drama", 2018, null, false));

            // fetch all movies
            log.info("Movies found with findAll():");
            log.info("----------------------------");
            for (Movie movie : repository.findAll()) {
                log.info(movie.toString());
            }
            log.info("");

            // fetch movies by director
            log.info("Movies found with search('nolan'):");
            log.info("----------------------------------");
            for (Movie movie : repository.search("nolan")) {
                log.info(movie.toString());
            }
            log.info("");
            log.info("Watched movies: {} of {}", repository.countByWatchedTrue(), repository.count());
            log.info("Movie Watchlist is ready - open http://localhost:8080 in your browser");
        };
    }

}
