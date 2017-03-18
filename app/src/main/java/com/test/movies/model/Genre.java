package com.test.movies.model;

import java.io.Serializable;

import io.realm.RealmObject;

/**
 * Created by andres on 16/03/17.
 */

public class Genre extends RealmObject implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;

    public Genre() {

    }

    public Genre(String name) {
        this.name = name;
    }

    /**
     * Get genre name
     *
     * @return
     */
    public String getName() {
        return name;
    }

}
