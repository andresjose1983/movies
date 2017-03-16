package com.test.movies.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by andres on 16/03/17.
 */

public class Movie implements Serializable {

    private int id;

    @SerializedName("backdrop_path")
    private String backdropPath;

    private Genre genre;

    @SerializedName("original_language")
    private String originalLanguage;

    @SerializedName("original_title")
    private String originalTitle;

    private String overview;

    @SerializedName("release_date")
    private String date;

    @SerializedName("poster_path")
    private String posterPath;

    private String title;

    @SerializedName("vote_average")
    private float vote;

    public Movie(int id, String backdropPath, String originalLanguage, String originalTitle,
                 String overview, String date, String posterPath, String title, float vote) {
        this.id = id;
        this.backdropPath = backdropPath;
        this.originalLanguage = originalLanguage;
        this.originalTitle = originalTitle;
        this.overview = overview;
        this.date = date;
        this.posterPath = posterPath;
        this.title = title;
        this.vote = vote;
    }

    /**
     * Set movie genre
     * @param genre
     */
    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    /**
     * Get id
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     * Get back image
     * @return
     */
    public String getBackdropPath() {
        return backdropPath;
    }

    /**
     * Get genre
     * @return
     */
    public Genre getGenre() {
        return genre;
    }

    /**
     * Get original lang
     * @return
     */
    public String getOriginalLanguage() {
        return originalLanguage;
    }

    /**
     * Get original title
     * @return
     */
    public String getOriginalTitle() {
        return originalTitle == null?"":originalTitle;
    }

    /**
     * Get content
     * @return
     */
    public String getOverview() {
        return overview == null?"":overview;
    }

    /**
     * Get date
     * @return
     */
    public String getDate() {
        return date;
    }

    /**
     * Get poster image
     * @return
     */
    public String getPosterPath() {
        return posterPath;
    }

    /**
     * Get title
     * @return
     */
    public String getTitle() {
        return title == null?"":title;
    }

    /**
     * Get movie vote
     * @return
     */
    public float getVote() {
        return vote;
    }

}
