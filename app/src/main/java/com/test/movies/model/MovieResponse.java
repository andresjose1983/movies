package com.test.movies.model;

import java.io.Serializable;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by andres on 16/03/17.
 */

public class MovieResponse extends RealmObject implements Serializable {

    private static final long serialVersionUID = 1L;

    private RealmList<Movie> results;

    private Genre genre;

    public MovieResponse() {
    }

    public MovieResponse(RealmList<Movie> results) {
        this.results = results;
    }

    public List<Movie> getResults() {
        return results;
    }

    public void setResults(RealmList<Movie> results) {
        this.results = results;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }
}
