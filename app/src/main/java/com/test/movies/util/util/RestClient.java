package com.test.movies.util.util;

import com.test.movies.BuildConfig;
import com.test.movies.model.MovieResponse;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

/**
 * Created by andres on 16/03/17.
 */

public final class RestClient {

    private static IClient mIClient;

    static {
        // init retrofit client
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.API_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();

        mIClient = retrofit.create(IClient.class);
    }

    /**
     * Get movies by popular
     *
     * @return Observable<MovieResponse>
     */
    public static Observable<MovieResponse> getMoviesByPopular() {
        return mIClient.getMoviesByPopular(BuildConfig.API_KEY);
    }

    /**
     * Get movies by top rated
     *
     * @return Observable<MovieResponse>
     */
    public static Observable<MovieResponse> getMoviesByTopRated() {
        return mIClient.getMoviesByTopRated(BuildConfig.API_KEY);
    }

    /**
     * Get movies by upcoming
     *
     * @return Observable<MovieResponse>
     */
    public static Observable<MovieResponse> getMoviesByUpcoming() {
        return mIClient.getMoviesByUpcoming(BuildConfig.API_KEY);
    }

}
