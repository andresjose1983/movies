package com.test.movies.interactor;

import com.test.movies.MovieApp;
import com.test.movies.R;
import com.test.movies.contract.MoviesListContract;
import com.test.movies.model.Genre;
import com.test.movies.util.util.RestClient;

import java.util.Arrays;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by andres on 18/03/17.
 */

public class MovieListInteractor {

    private MoviesListContract.Presenter mPresenter;

    /**
     * Init listener action
     *
     * @param presenter
     */
    public MovieListInteractor(MoviesListContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    /**
     * Call server resource common
     */
    public void call() {
        Observable.combineLatest(RestClient.getMoviesByPopular(),
                        RestClient.getMoviesByTopRated(), RestClient.getMoviesByUpcoming(), (a, b, c) -> {
                            a.setGenre(new Genre(MovieApp.getInstance().getString(R.string.popular)));
                            b.setGenre(new Genre(MovieApp.getInstance().getString(R.string.top_rated)));
                            c.setGenre(new Genre(MovieApp.getInstance().getString(R.string.upcoming)));
                    return Arrays.asList(a, b, c);
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(() -> mPresenter.showSwipeView(true))
                .doAfterTerminate(() -> mPresenter.showSwipeView(false))
                .subscribe(movieResponses -> {
                    if (mPresenter != null)
                        mPresenter.onSuccess(movieResponses);
                }, t -> {
                    if (mPresenter != null)
                        mPresenter.onFailure(t.getMessage());
                });
    }
}
