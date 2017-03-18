package com.test.movies.interactor;

import com.test.movies.MovieApp;
import com.test.movies.R;
import com.test.movies.contract.MoviesListContract;
import com.test.movies.model.Genre;
import com.test.movies.model.MovieResponse;
import com.test.movies.util.util.RestClient;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by andres on 18/03/17.
 */

public class MovieListInteractor {

    private MoviesListContract.Presenter mIListener;

    /**
     * Init listener action
     *
     * @param mIListener
     */
    public MovieListInteractor(MoviesListContract.Presenter mIListener) {
        this.mIListener = mIListener;
    }

    /**
     * Call server resource
     */
    public void getMoviesByPopular() {
        call(new Genre(MovieApp.getInstance().getString(R.string.popular)),
                RestClient.getMoviesByPopular());
    }

    /**
     * Call server resource
     */
    public void getMoviesByTopRated() {
        call(new Genre(MovieApp.getInstance().getString(R.string.top_rated)),
                RestClient.getMoviesByTopRated());
    }

    /**
     * Call server resource
     */
    public void getMoviesByUpcoming() {
        call(new Genre(MovieApp.getInstance().getString(R.string.upcoming)),
                RestClient.getMoviesByUpcoming());
    }

    /**
     * Call server resource common
     *
     * @param genre
     * @param movies
     */
    private void call(final Genre genre, Observable<MovieResponse> movies) {
        movies.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Action1<MovieResponse>() {
                    @Override
                    public void call(MovieResponse movieResponse) {
                        if (mIListener != null) {
                            movieResponse.setGenre(genre);
                            mIListener.onSuccess(movieResponse);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        if (mIListener != null)
                            mIListener.onFailure(throwable.getMessage());
                    }
                });
    }
}
