package com.test.movies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;
import com.test.movies.contract.MoviesListContract;
import com.test.movies.model.MovieResponse;
import com.test.movies.presenter.MovieListPresenter;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MoviesListActivity extends AppCompatActivity implements MoviesListContract.View {

    @BindView(R.id.rvMovie)
    RecyclerView mRvMovie;

    private MovieListPresenter mMovieListPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_list);
        ButterKnife.bind(this);
        init();

        mMovieListPresenter = new MovieListPresenter(this);

        mMovieListPresenter.getMoviesByPopular();
        mMovieListPresenter.getMoviesByUpcoming();
        mMovieListPresenter.getMoviesByTopRated();
    }

    @Override
    public void showMovies(MovieResponse movieResponse) {
        Log.d("Hola", movieResponse.toString());
    }

    /**
     * Show error message
     * @param error
     */
    @Override
    public void showError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    private void init() {

    }

}
