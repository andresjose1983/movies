package com.test.movies.interactor;

import android.support.annotation.NonNull;

import com.test.movies.MovieApp;
import com.test.movies.R;
import com.test.movies.contract.MoviesListContract;
import com.test.movies.model.Genre;
import com.test.movies.model.Movie;
import com.test.movies.model.MovieResponse;
import com.test.movies.util.util.RestClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmQuery;
import io.realm.RealmResults;
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
                .map(new Func1<List<MovieResponse>, List<MovieResponse>>() {
                    @Override
                    public List<MovieResponse> call(List<MovieResponse> movieResponses) {
                        // Get a Realm instance for this thread
                        Realm realm = Realm.getDefaultInstance();
                        realm.beginTransaction();
                        realm.deleteAll();
                        for (MovieResponse movieResponse : movieResponses) {
                            List<Movie> results = movieResponse.getResults();
                            for (Movie movie : results) {
                                movie.setGenre(movieResponse.getGenre());
                                realm.copyToRealmOrUpdate(movie);
                            }
                        }
                        realm.commitTransaction();
                        realm.close();
                        return movieResponses;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends List<MovieResponse>>>() {
                    @Override
                    public Observable<? extends List<MovieResponse>> call(Throwable throwable) {
                        return Observable.just(getMovieResponses());
                    }
                })
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

    /**
     * Get movies from databases
     */
    public void getMovieResponse() {
        getMovieResponseByDescription(null);
    }

    /**
     * @return List<MovieResponse>
     */
    private List<MovieResponse> getMovieResponses() {
        Realm realm = Realm.getDefaultInstance();
        return getMovieResponses(equalTo(where(realm, null), R.string.popular),
                equalTo(where(realm, null), R.string.top_rated),
                equalTo(where(realm, null), R.string.upcoming));
    }

    /**
     * Commom observable search
     *
     * @param description
     * @return Observable<List<MovieResponse>>
     */
    private Observable<List<MovieResponse>> getMovieResponse(String description) {
        final Realm realm = Realm.getDefaultInstance();

        return Observable.combineLatest(
                equalTo(where(realm, description), R.string.popular).asObservable(),
                equalTo(where(realm, description), R.string.top_rated).asObservable(),
                equalTo(where(realm, description), R.string.upcoming).asObservable(),
                new Func3<RealmResults<Movie>, RealmResults<Movie>, RealmResults<Movie>,
                        List<MovieResponse>>() {
                    @Override
                    public List<MovieResponse> call(RealmResults<Movie> movies,
                                                    RealmResults<Movie> movies2,
                                                    RealmResults<Movie> movies3) {
                        return getMovieResponses(movies, movies2, movies3);
                    }
                }
        );
    }

    /**
     * @param movies
     * @param movies2
     * @param movies3
     * @return List<MovieResponse>
     */
    @NonNull
    private List<MovieResponse> getMovieResponses(RealmResults<Movie> movies,
                                                  RealmResults<Movie> movies2,
                                                  RealmResults<Movie> movies3) {
        List<MovieResponse> movieResponses = new ArrayList<>();
        if (movies.size() > 0)
            movieResponses.add(create(movies));
        if (movies2.size() > 0)
            movieResponses.add(create(movies2));
        if (movies3.size() > 0)
            movieResponses.add(create(movies3));
        return movieResponses;
    }

    /**
     * Get movies from databases by description
     */
    public void getMovieResponseByDescription(String description) {

        getMovieResponse(description)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<MovieResponse>>() {
                    @Override
                    public void call(List<MovieResponse> movieResponses) {
                        if (mPresenter != null)
                            mPresenter.onSuccess(movieResponses);
                    }
                });
    }

    /**
     * Create new movie response
     *
     * @param movies
     * @return
     */
    private MovieResponse create(final RealmResults<Movie> movies) {
        MovieResponse movieResponse = new MovieResponse();

        if (movies.size() > 0) {
            RealmList<Movie> realmList = new RealmList<>();
            movieResponse.setGenre(movies.get(0).getGenre());
            realmList.addAll(movies.subList(0, movies.size()));
            movieResponse.setResults(realmList);
            return movieResponse;
        }
        return null;
    }

    /**
     * Where validation white title
     *
     * @param realm
     * @param description
     * @return RealmQuery<Movie>
     */
    private RealmQuery<Movie> where(final Realm realm, String description) {
        RealmQuery<Movie> where = realm.where(Movie.class);

        if (description != null && !description.isEmpty())
            where.contains("title", description, Case.INSENSITIVE);
        return where;
    }

    /**
     * @param where
     * @param category
     * @return RealmResults<Movie>
     */
    private RealmResults<Movie> equalTo(RealmQuery<Movie> where, int category) {
        return where.equalTo("genre.name", MovieApp.getInstance().getString(category))
                .findAll();
    }
}
