package com.test.movies.presenter;

import com.test.movies.contract.MoviesListContract;
import com.test.movies.interactor.MovieListInteractor;
import com.test.movies.model.MovieResponse;

/**
 * Created by andres on 17/03/17.
 */

public class MovieListPresenter implements MoviesListContract.Presenter {

    private MoviesListContract.View mView;
    private MovieListInteractor mMovieListInteractor;

    /**
     * Init view implementations
     * @param mView
     */
    public MovieListPresenter(MoviesListContract.View mView) {
        this.mView = mView;
        mMovieListInteractor = new MovieListInteractor(this);
    }


    /**
     *  Get movies by popular
     */
    @Override
    public void getMoviesByPopular() {
        mMovieListInteractor.getMoviesByPopular();
    }

    /**
     *  Get movies by Upcoming
     */
    @Override
    public void getMoviesByUpcoming() {
        mMovieListInteractor.getMoviesByUpcoming();
    }

    /**
     * Get movies by TopRated
     */
    @Override
    public void getMoviesByTopRated() {
        mMovieListInteractor.getMoviesByTopRated();
    }

    /**
     * Return data request from server
     * @param movieResponse
     */
    @Override
    public void onSuccess(MovieResponse movieResponse) {
        mView.showMovies(movieResponse);
    }

    /**
     * Return message from server
     * @param message
     */
    @Override
    public void onFailure(String message) {
        mView.showError(message);
    }
}
