package com.example.moviewatchlist;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

/**
 * A movie on the watchlist (JPA entity).
 */
@Entity
public class Movie {

    @Id
    @GeneratedValue
    private Long id;

    private String title;

    private String director;

    private String genre;

    private Integer releaseYear;

    private Integer rating;

    private Boolean watched = false;

    protected Movie() {
    }

    public Movie(String title, String director, String genre, Integer releaseYear,
            Integer rating, Boolean watched) {
        this.title = title;
        this.director = director;
        this.genre = genre;
        this.releaseYear = releaseYear;
        this.rating = rating;
        this.watched = watched;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Integer getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Boolean getWatched() {
        return watched;
    }

    public void setWatched(Boolean watched) {
        this.watched = watched;
    }

    @Override
    public String toString() {
        return String.format("Movie[id=%d, title='%s', director='%s', year=%d, rating=%d, watched=%s]",
                id, title, director, releaseYear, rating, watched);
    }

}
