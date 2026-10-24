package com.example.moviewatchlist;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    /**
     * Finds movies whose title or director contains the given text (case-insensitive).
     */
    List<Movie> findByTitleContainingIgnoreCaseOrDirectorContainingIgnoreCase(String title, String director);

    long countByWatchedTrue();

    default List<Movie> search(String text) {
        return findByTitleContainingIgnoreCaseOrDirectorContainingIgnoreCase(text, text);
    }
}
