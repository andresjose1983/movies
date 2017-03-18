package com.test.movies.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by andres on 16/03/17.
 */

public class MovieResponse implements Serializable {

    private List<Movie> results = new ArrayList<>();

    private Genre genre;

    public MovieResponse() {
    }

    public MovieResponse(List<Movie> results) {
        this.results = results;
    }

    public List<Movie> getResults() {
        return results;
    }

    public void setResults(List<Movie> results) {
        this.results = results;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }
}
