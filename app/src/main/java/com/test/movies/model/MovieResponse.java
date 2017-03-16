package com.test.movies.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by andres on 16/03/17.
 */

public class MovieResponse implements Serializable {

    private List<Movie> results = new ArrayList<>();

    public MovieResponse() {
    }

    public MovieResponse(List<Movie> results) {
        this.results = results;
    }

    public List<Movie> getResults() {
        return results;
    }

}
