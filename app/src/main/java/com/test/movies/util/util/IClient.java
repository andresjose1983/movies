package com.test.movies.util.util;

import com.test.movies.model.MovieResponse;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by andres on 16/03/17.
 */

public interface IClient {

    /**
     * Get popular movies
     *
     * @param apiKey
     * @return Observable<MovieResponse>
     */
    @GET("movie/popular")
    Observable<MovieResponse> getMoviesByPopular(@Query("api_key") String apiKey);

    /**
     * Get top rated movies
     *
     * @param apiKey
     * @return Observable<MovieResponse>
     */
    @GET("movie/top_rated")
    Observable<MovieResponse> getMoviesByTopRated(@Query("api_key") String apiKey);

    /**
     * Get top rated movies
     *
     * @param apiKey
     * @return Observable<MovieResponse>
     */
    @GET("movie/upcoming")
    Observable<MovieResponse> getMoviesByUpcoming(@Query("api_key") String apiKey);

}
