package com.appdesk.movieapp.remote

import com.appdesk.movieapp.data.MovieResponse
import com.appdesk.movieapp.data.Result
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieInterface {

    @GET("/3/movie/popular")
    suspend fun getAllPopularMovies(
        @Query("api_key")apiKey:String
    ): Response<MovieResponse>

    @GET("3/movie/top_rated")
    suspend fun getAllTopRatedMovies(
        @Query("api_key")apiKey:String
    ): Response<MovieResponse>

    @GET("3/movie/{movie_id}")
    suspend fun getMoviesDetails(
        @Path("movie_id") id: Int,
        @Query("api_key")apiKey:String
    ): Response<Result>

}