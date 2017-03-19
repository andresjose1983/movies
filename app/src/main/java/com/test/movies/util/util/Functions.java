package com.test.movies.util.util;

import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.support.v4.content.ContextCompat;
import android.widget.RatingBar;

import com.test.movies.R;

/**
 * Created by andres on 19/03/17.
 */

public class Functions {

    /**
     * Change star ratng color
     *
     * @param ratingBar
     */
    public static void changeRatingColor(RatingBar ratingBar) {
        LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(ContextCompat.getColor(ratingBar.getContext(),
                R.color.blue), PorterDuff.Mode.SRC_ATOP);
        stars.getDrawable(1).setColorFilter(ContextCompat.getColor(ratingBar.getContext(),
                R.color.blue), PorterDuff.Mode.SRC_ATOP);
        stars.getDrawable(0).setColorFilter(ContextCompat.getColor(ratingBar.getContext(),
                R.color.blue), PorterDuff.Mode.SRC_ATOP);
    }
}
