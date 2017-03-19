package com.test.movies.adapter;

import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.test.movies.BuildConfig;
import com.test.movies.R;
import com.test.movies.contract.MoviesListContract;
import com.test.movies.model.Movie;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by andres on 19/03/17.
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {

    private List<Movie> mMovies;
    private MoviesListContract.View mIMainView;

    public MoviesAdapter(MoviesListContract.View mIMainView, List<Movie> mMovies) {
        this.mMovies = mMovies;
        this.mIMainView = mIMainView;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Movie movie = mMovies.get(position);
        holder.mTvTitle.setText(movie.getTitle());
        holder.mTvDate.setText(movie.getDate());
        holder.mRbMovie.setRating(movie.getVote());
        Picasso.with(holder.mIvMovie.getContext()).load(BuildConfig.URL_IMAGE + movie.getBackdropPath())
                .into(holder.mIvMovie);
    }

    @Override
    public int getItemCount() {
        return mMovies == null ? 0 : mMovies.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_movie)
        ImageView mIvMovie;
        @BindView(R.id.tv_title)
        TextView mTvTitle;
        @BindView(R.id.rb_movie)
        RatingBar mRbMovie;
        @BindView(R.id.tv_date)
        TextView mTvDate;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            changeRatingColor(mRbMovie);
        }
    }

    /**
     * Change star ratng color
     *
     * @param ratingBar
     */
    public void changeRatingColor(RatingBar ratingBar) {
        LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(ContextCompat.getColor(ratingBar.getContext(),
                R.color.blue), PorterDuff.Mode.SRC_ATOP);
        stars.getDrawable(1).setColorFilter(ContextCompat.getColor(ratingBar.getContext(),
                R.color.blue), PorterDuff.Mode.SRC_ATOP);
        stars.getDrawable(0).setColorFilter(ContextCompat.getColor(ratingBar.getContext(),
                R.color.blue), PorterDuff.Mode.SRC_ATOP);
    }
}
