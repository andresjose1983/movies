package com.test.movies.presenter;

import com.test.movies.contract.MovieDetailContract;
import com.test.movies.interactor.MovieInteractor;
import com.test.movies.model.Movie;

/**
 * Created by andres on 19/03/17.
 */

public class MovieDetailPresenter implements MovieDetailContract.Presenter {

    private MovieDetailContract.View mView;
    private MovieInteractor mMovieInteractor;

    public MovieDetailPresenter(MovieDetailContract.View mView) {
        this.mView = mView;
        mMovieInteractor = new MovieInteractor(this);
    }

    @Override
    public void getMovieById(int id) {
        mMovieInteractor.getMoviebyId(id);
    }

    @Override
    public void showMovieDetail(Movie movie) {
        mView.showDetail(movie);
    }
}
