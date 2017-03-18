package com.test.movies.interactor;

import com.test.movies.MovieApp;
import com.test.movies.R;
import com.test.movies.contract.MoviesListContract;
import com.test.movies.model.Genre;
import com.test.movies.model.MovieResponse;
import com.test.movies.util.util.RestClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
     */
    private void call(final Genre genre, Call<MovieResponse> movies){
        movies.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (mIListener != null) {
                    MovieResponse body = response.body();
                    body.setGenre(genre);
                    mIListener.onSuccess(body);
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                if (mIListener != null)
                    mIListener.onFailure(t.getMessage());
            }
        });
    }
}
