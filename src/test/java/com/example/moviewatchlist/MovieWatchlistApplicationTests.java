package com.example.moviewatchlist;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.BDDAssertions.then;

@SpringBootTest
public class MovieWatchlistApplicationTests {

    @Autowired
    private MovieRepository repository;

    @Test
    public void shouldLoadSampleMoviesWhenTheApplicationIsStarted() {
        then(this.repository.count()).isEqualTo(7);
    }

    @Test
    public void shouldFindMoviesByDirector() {
        then(this.repository.search("nolan")).hasSize(2);
    }

    @Test
    public void shouldCountWatchedMovies() {
        then(this.repository.countByWatchedTrue()).isEqualTo(4);
    }
}
