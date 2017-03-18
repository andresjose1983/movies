package com.test.movies.presenter;

import com.test.movies.contract.MoviesListContract;
import com.test.movies.interactor.MovieListInteractor;
import com.test.movies.model.MovieResponse;

import java.util.List;

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
     * Call server
     */
    @Override
    public void call() {
        mMovieListInteractor.call();
    }

    /**
     * Return data request from server
     * @param movieResponses
     */
    @Override
    public void onSuccess(List<MovieResponse> movieResponses) {
        mView.showMovies(movieResponses);
    }

    /**
     * Return message from server
     * @param message
     */
    @Override
    public void onFailure(String message) {
        mView.showError(message);
    }

    /**
     * Show swipe when it is requesting to server
     * @param show
     */
    @Override
    public void showSwipeView(boolean show) {
        if(show)
            mView.showSwipe();
        else
            mView.hideSwipe();
    }
}
