package com.test.movies;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.test.movies.contract.MovieDetailContract;
import com.test.movies.model.Movie;
import com.test.movies.presenter.MovieDetailPresenter;
import com.test.movies.util.util.Functions;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieDetailActivity extends AppCompatActivity implements MovieDetailContract.View {

    public static final String INTENT_DATA = "com.example.andres.movies_test.data.INTENT_DATA";
    @BindView(R.id.tb_movie)
    Toolbar mTbPhoto;
    @BindView(R.id.iv_movie)
    ImageView mIvMovie;
    @BindView(R.id.iv_movie_icon)
    ImageView mIvMovieIcon;
    @BindView(R.id.tv_date)
    TextView mTvDate;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_overview)
    TextView mTvOverview;
    @BindView(R.id.rb_movie)
    RatingBar mRbMovie;

    int mIdMovie;

    private MovieDetailPresenter mMovieDetailPresenter;

    public static void show(final MoviesListActivity moviesListActivity, final Movie movie,
                            final ImageView ivMovie) {
        Intent intent = new Intent(moviesListActivity, MovieDetailActivity.class)
                .putExtra(INTENT_DATA, movie.getId());

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) {
            Pair<View, String> p1 = Pair.create((View) ivMovie, "movie_picture");

            ActivityOptionsCompat options = ActivityOptionsCompat
                    .makeSceneTransitionAnimation(moviesListActivity, p1);
            moviesListActivity.startActivity(intent, options.toBundle());
        } else
            moviesListActivity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        ButterKnife.bind(this);
        init();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                supportFinishAfterTransition();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void init() {

        mIdMovie = (int) getIntent().getExtras().get(INTENT_DATA);

        setSupportActionBar(mTbPhoto);
        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        mMovieDetailPresenter = new MovieDetailPresenter(this);
        mMovieDetailPresenter.getMovieById(mIdMovie);
    }

    @Override
    public void showDetail(Movie movie) {
        Picasso.with(this).load(BuildConfig.URL_IMAGE_POST + movie.getPosterPath())
                .fit().centerCrop().into(mIvMovie);
        Picasso.with(this).load(BuildConfig.URL_IMAGE_POST + movie.getBackdropPath())
                .fit().centerCrop().into(mIvMovieIcon);

        mTbPhoto.setTitle(movie.getTitle());
        mTvDate.setText(movie.getDate());
        mTvTitle.setText(movie.getOriginalTitle());
        mTvOverview.setText(movie.getOverview());
        mRbMovie.setRating(movie.getVote());

        Functions.changeRatingColor(mRbMovie);
    }
}
