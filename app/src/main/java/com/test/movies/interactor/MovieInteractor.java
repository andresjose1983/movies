package com.test.movies.interactor;

import com.test.movies.contract.MovieDetailContract;
import com.test.movies.model.Movie;

import io.realm.Realm;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by andres on 19/03/17.
 */

public class MovieInteractor {

    private MovieDetailContract.Presenter mPresenter;

    public MovieInteractor(MovieDetailContract.Presenter mPresenter) {
        this.mPresenter = mPresenter;
    }

    /**
     * Get movie by id
     *
     * @param id
     */
    public void getMoviebyId(int id) {
        Realm realm = Realm.getDefaultInstance();
        realm.where(Movie.class).equalTo("id", id).findFirst()
                .<Movie>asObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Movie>() {
                    @Override
                    public void call(Movie realmObject) {
                        if (mPresenter != null)
                            mPresenter.showMovieDetail(realmObject);
                    }
                });

    }


}
