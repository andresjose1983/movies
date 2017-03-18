package com.test.movies;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.test.movies.contract.MoviesListContract;
import com.test.movies.model.MovieResponse;
import com.test.movies.presenter.MovieListPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MoviesListActivity extends AppCompatActivity implements MoviesListContract.View {

    @BindView(R.id.rvMovie)
    RecyclerView mRvMovie;

    @BindView(R.id.srlMovie)
    SwipeRefreshLayout mSrlMovie;

    private MovieListPresenter mMovieListPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_list);
        ButterKnife.bind(this);
        init();

        mMovieListPresenter = new MovieListPresenter(this);

        mMovieListPresenter.call();
    }

    @Override
    public void showMovies(List<MovieResponse> movieResponses) {
        Log.d("Hola", movieResponses.toString());
    }

    /**
     * Show error message
     *
     * @param error
     */
    @Override
    public void showError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    /**
     * show sipe refresh
     */
    @Override
    public void showSwipe() {
        mSrlMovie.setRefreshing(true);
    }

    /**
     * Hide sipe refresh
     */
    @Override
    public void hideSwipe() {
        mSrlMovie.setRefreshing(false);
    }

    /**
     * Init widget
     */
    private void init() {
        mSrlMovie.setColorSchemeResources(R.color.orange, R.color.green, R.color.blue);
    }

}
