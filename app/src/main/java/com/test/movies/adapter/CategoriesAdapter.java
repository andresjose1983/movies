package com.test.movies.adapter;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.test.movies.R;
import com.test.movies.contract.MoviesListContract;
import com.test.movies.model.MovieResponse;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by andres on 19/03/17.
 */

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ViewHolder> {

    private List<MovieResponse> mCategories;
    private MoviesListContract.View mIMainView;

    public CategoriesAdapter(MoviesListContract.View mIMainView) {
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

    /**
     * Clear list
     */
    private void clear() {
        if (mCategories != null)
            mCategories = new ArrayList<>();
    }

    /**
     * Add movies list
     *
     * @param categories
     */
    public void addAll(List<MovieResponse> categories) {
        clear();
        mCategories = categories;
        notifyDataSetChanged();
    }

}
