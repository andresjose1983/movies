package com.test.movies.util.util;

import com.test.movies.model.MovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by andres on 16/03/17.
 */

public interface IClient {

    /**
     * Get popular movies
     * @param apiKey
     * @return
     */
    @GET("popular")
    Call<MovieResponse> getMoviesByPopular(@Query("api_key") String apiKey);

    /**
     * Get top rated movies
     * @param apiKey
     * @return
     */
    @GET("top_rated")
    Call<MovieResponse> getMoviesByTopRated(@Query("api_key") String apiKey);

    /**
     * Get top rated movies
     * @param apiKey
     * @return
     */
    @GET("upcoming")
    Call<MovieResponse> getMoviesByUpcoming(@Query("api_key") String apiKey);

}