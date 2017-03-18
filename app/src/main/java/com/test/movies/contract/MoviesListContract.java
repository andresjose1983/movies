package com.test.movies.contract;

import com.test.movies.model.Movie;
import com.test.movies.model.MovieResponse;

/**
 * Created by andres on 16/03/17.
 */

public class MoviesListContract {

    /**
     * MovieListView contract view
     */
    public interface View{

        /**
         * Display data
         * @param movieResponse
         */
        void showMovies(MovieResponse movieResponse);

        /**
         * Display error
         * @param error
         */
        void showError(String error);
    }

    public interface Presenter{

        /**
         * Get movies by popular
         */
        void getMoviesByPopular();

        /**
         * Get movies by Upcoming
         */
        void getMoviesByUpcoming();

        /**
         * Get movies by TopRated
         */
        void getMoviesByTopRated();

        /**
         *  Return data request from server
         * @param movieResponse
         */
        void onSuccess(MovieResponse movieResponse);

        /**
         * Return message from server
         *
         * @param message
         */
        void onFailure(String message);

    }
}
