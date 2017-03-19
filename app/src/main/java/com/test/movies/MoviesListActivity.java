package com.test.movies;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.Toast;

import com.test.movies.adapter.CategoriesAdapter;
import com.test.movies.contract.MoviesListContract;
import com.test.movies.model.Movie;
import com.test.movies.model.MovieResponse;
import com.test.movies.presenter.MovieListPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MoviesListActivity extends AppCompatActivity implements MoviesListContract.View,
        SearchView.OnQueryTextListener {

    @BindView(R.id.rvMovie)
    RecyclerView mRvMovie;

    @BindView(R.id.srlMovie)
    SwipeRefreshLayout mSrlMovie;

    private MovieListPresenter mMovieListPresenter;
    private CategoriesAdapter mCategoriesAdapter;

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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        SearchView mSearchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        mSearchView.setOnQueryTextListener(this);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        mSearchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        return true;
    }

    @Override
    public void showMovies(List<MovieResponse> movieResponses) {
        mCategoriesAdapter.addAll(movieResponses);
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
        mSrlMovie.setEnabled(true);
    }

    /**
     * Hide sipe refresh
     */
    @Override
    public void hideSwipe() {
        mSrlMovie.setRefreshing(false);
        mSrlMovie.setEnabled(false);
    }

    @Override
    public void gotoMovieDetail(Movie movie, ImageView ivMovie) {
        MovieDetailActivity.show(this, movie, ivMovie);
    }

    /**
     * Init widget
     */
    private void init() {
        mSrlMovie.setColorSchemeResources(R.color.orange, R.color.green, R.color.blue);

        mRvMovie.setLayoutManager(new LinearLayoutManager(this));
        mRvMovie.setHasFixedSize(true);
        mRvMovie.setItemAnimator(new DefaultItemAnimator());
        mRvMovie.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mRvMovie.setAdapter(mCategoriesAdapter = new CategoriesAdapter(this));
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (newText.isEmpty()) {
            mMovieListPresenter.getMovieResponse();
            return false;
        }
        mMovieListPresenter.getMovieResponseByDescription(newText);

        return false;
    }
}
