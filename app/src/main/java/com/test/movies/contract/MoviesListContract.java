package com.test.movies.contract;

import com.test.movies.model.MovieResponse;

import java.util.List;

/**
 * Created by andres on 16/03/17.
 */

public class MoviesListContract {

    /**
     * MovieListView contract view
     */
    public interface View {

        /**
         * Display data
         *
         * @param movieResponses
         */
        void showMovies(List<MovieResponse> movieResponses);

        /**
         * Display error
         *
         * @param error
         */
        void showError(String error);

        /**
         * Show swipe refreshe view
         */
        void showSwipe();

        /**
         * Hide swipe refresh view
         */
        void hideSwipe();
    }

    public interface Presenter {

        /**
         * Get movie requests
         */
        void call();

        /**
         * Return data request from server
         *
         * @param movieResponses
         */
        void onSuccess(List<MovieResponse> movieResponses);

        /**
         * Return message from server
         *
         * @param message
         */
        void onFailure(String message);

        /**
         * Show swipe when it is requesting to server
         *
         * @param show
         */
        void showSwipeView(boolean show);

    }
}
