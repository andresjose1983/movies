package com.test.movies.contract;

import com.test.movies.model.Movie;

/**
 * Created by andres on 19/03/17.
 */

public class MovieDetailContract {

    public interface View{

        /**
         * Show movie detail
         * @param movie
         */
        void showDetail(Movie movie);

    }

    public interface Presenter{

        /**
         * Get movie by id
         * @param id
         */
        void getMovieById(int id);

        /**
         * Show movie detail
         * @param movie
         */
        void showMovieDetail(Movie movie);
    }
}
