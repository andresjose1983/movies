package com.test.movies.model;

import java.io.Serializable;

/**
 * Created by andres on 16/03/17.
 */

public class Genre implements Serializable {

    private String name;

    public Genre(String name) {
        this.name = name;
    }

    /**
     * Get genre name
     * @return
     */
    public String getName() {
        return name;
    }

}
