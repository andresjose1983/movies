package com.test.movies.interactor;

import com.test.movies.MovieApp;
import com.test.movies.R;
import com.test.movies.contract.MoviesListContract;
import com.test.movies.model.Genre;
import com.test.movies.model.MovieResponse;
import com.test.movies.util.util.RestClient;

import java.util.Arrays;
import java.util.List;

import io.realm.Realm;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func3;
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
                RestClient.getMoviesByTopRated(), RestClient.getMoviesByUpcoming(),
                new Func3<MovieResponse, MovieResponse, MovieResponse, List<MovieResponse>>() {
                    @Override
                    public List<MovieResponse> call(MovieResponse movieResponse,
                                                    MovieResponse movieResponse2,
                                                    MovieResponse movieResponse3) {
                        movieResponse.setGenre(new Genre(MovieApp.getInstance()
                                .getString(R.string.popular)));
                        movieResponse2.setGenre(new Genre(MovieApp.getInstance()
                                .getString(R.string.top_rated)));
                        movieResponse3.setGenre(new Genre(MovieApp.getInstance()
                                .getString(R.string.upcoming)));
                        return Arrays.asList(movieResponse, movieResponse2, movieResponse3);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mPresenter.showSwipeView(true);
                    }
                })
                .doAfterTerminate(new Action0() {
                    @Override
                    public void call() {
                        mPresenter.showSwipeView(false);
                    }
                })
                .map(new Func1<List<MovieResponse>, List<MovieResponse>>() {
                    @Override
                    public List<MovieResponse> call(List<MovieResponse> movieResponses) {
                        // Get a Realm instance for this thread
                        Realm realm = Realm.getDefaultInstance();
                        realm.beginTransaction();
                        realm.copyToRealmOrUpdate(movieResponses);
                        realm.commitTransaction();
                        return movieResponses;
                    }
                })
                .subscribe(new Action1<List<MovieResponse>>() {
                    @Override
                    public void call(List<MovieResponse> movieResponses) {
                        if (mPresenter != null)
                            mPresenter.onSuccess(movieResponses);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        if (mPresenter != null)
                            mPresenter.onFailure(throwable.getMessage());
                    }
                });
    }
}
