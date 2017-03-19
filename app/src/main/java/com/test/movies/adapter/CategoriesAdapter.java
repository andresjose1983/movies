package com.test.movies.adapter;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.test.movies.R;
import com.test.movies.contract.MoviesListContract;
import com.test.movies.model.Movie;
import com.test.movies.model.MovieResponse;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by andres on 19/03/17.
 */

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ViewHolder> implements Filterable {

    private List<MovieResponse> mCategories;
    private List<MovieResponse> mCategoriesFilter = new ArrayList<>();
    private MoviesListContract.View mIMainView;

    public CategoriesAdapter(MoviesListContract.View mIMainView, List<MovieResponse> mCategories) {
        this.mCategories = mCategories;
        this.mCategoriesFilter = new ArrayList<>(this.mCategories);
        this.mIMainView = mIMainView;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category,
                parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final MovieResponse genre = mCategories.get(position);
        holder.mTvTitle.setText(genre.getGenre().getName());
        holder.mRvMovies.setAdapter(new MoviesAdapter(mIMainView, genre.getResults()));
    }

    @Override
    public int getItemCount() {
        return mCategories == null ? 0 : mCategories.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_title)
        TextView mTvTitle;

        @BindView(R.id.rv_movies)
        RecyclerView mRvMovies;

        @BindView(R.id.iv_direction)
        ImageView mIvDirection;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mRvMovies.getVisibility() == View.GONE) {
                        mIvDirection.setImageResource(R.drawable.ic_keyboard_arrow_up_24px);
                        mRvMovies.setVisibility(View.VISIBLE);
                    } else {
                        mIvDirection.setImageResource(R.drawable.ic_keyboard_arrow_down_24px);
                        mRvMovies.setVisibility(View.GONE);
                    }
                }
            });
            init();
        }

        private void init() {
            mRvMovies.setLayoutManager(new LinearLayoutManager(mRvMovies.getContext()));
            mRvMovies.setHasFixedSize(true);
            mRvMovies.setItemAnimator(new DefaultItemAnimator());
            mRvMovies.addItemDecoration(new DividerItemDecoration(mRvMovies.getContext(),
                    DividerItemDecoration.VERTICAL));
        }
    }

    @Override
    public Filter getFilter() {
        return new GenreFilter(this, mCategoriesFilter);
    }

    public void clear() {
        mCategories.clear();
        notifyDataSetChanged();
    }

    private static class GenreFilter extends Filter {

        private List<MovieResponse> mFiltersCategories = new ArrayList<>();
        final private List<MovieResponse> mCategories;
        final private CategoriesAdapter mCategoriesAdapter;

        public GenreFilter(CategoriesAdapter mCategoriesAdapter, List<MovieResponse> mCategories) {
            this.mCategoriesAdapter = mCategoriesAdapter;
            this.mCategories = mCategories;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            mFiltersCategories.clear();
            final FilterResults results = new FilterResults();

            if (constraint.length() == 0) {
                mFiltersCategories.addAll(mCategories);
            } else {
                for (final MovieResponse movieResponse : mCategories) {
                    List<Movie> movies = new ArrayList<>();
                    for (Movie movie : movieResponse.getResults()) {
                        try {
                            float rating = Float.parseFloat((String) constraint);
                            if (movie.getVote() >= rating)
                                movies.add(movie);
                        } catch (NumberFormatException e) {
                            boolean verify = movie.getTitle().toUpperCase().contains(constraint)
                                    || movie.getOverview().toUpperCase().contains(constraint)
                                    || movie.getOriginalTitle().toUpperCase().contains(constraint);
                            if (verify)
                                movies.add(movie);
                        }
                    }
                    if (!movies.isEmpty()) {
                        movieResponse.getResults().clear();
                        movieResponse.getResults().addAll(movies);
                        mFiltersCategories.add(movieResponse);
                    }
                }
            }
            results.values = mFiltersCategories;
            results.count = mFiltersCategories.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            mCategoriesAdapter.mCategories.clear();
            if (filterResults.values != null)
                mCategoriesAdapter.mCategories.addAll((ArrayList<MovieResponse>) filterResults.values);
            mCategoriesAdapter.notifyDataSetChanged();
        }
    }

    public void addAll(List<MovieResponse> genres) {
        mCategories = genres;
        notifyDataSetChanged();
    }

}
