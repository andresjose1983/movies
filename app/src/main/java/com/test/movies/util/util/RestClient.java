package com.test.movies.util.util;

import com.test.movies.BuildConfig;
import com.test.movies.model.MovieResponse;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by andres on 16/03/17.
 */

public final class RestClient {

    private static IClient mIClient;

    static{
        // init retrofit client
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();

        mIClient = retrofit.create(IClient.class);
    }

    /**
     * Get movies by popular
     * @return Call<MovieResponse>
     */
    public static Call<MovieResponse> getMoviesByPopular(){
        return mIClient.getMoviesByPopular(BuildConfig.API_KEY);
    }

    /**
     * Get movies by top rated
     * @return Call<MovieResponse>
     */
    public static Call<MovieResponse> getMoviesByTopRated(){
        return mIClient.getMoviesByTopRated(BuildConfig.API_KEY);
    }

    /**
     * Get movies by upcoming
     * @return Call<MovieResponse>
     */
    public static Call<MovieResponse> getMoviesByUpcoming(){
        return mIClient.getMoviesByUpcoming(BuildConfig.API_KEY);
    }

}
