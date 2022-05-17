package com.appdesk.movieapp.repository

import com.appdesk.movieapp.data.MovieResponse
import com.appdesk.movieapp.data.Result
import com.appdesk.movieapp.remote.MovieInterface
import com.appdesk.movieapp.utils.Constants
import retrofit2.Response
import javax.inject.Inject

class MovieDetailsRepository @Inject constructor(private val movieInterface: MovieInterface) {

    suspend fun getAllPopularMovies(): Response<MovieResponse> =
        movieInterface.getAllPopularMovies(Constants.API_KEY)

    suspend fun getAllTopRatedMovies(): Response<MovieResponse> =
        movieInterface.getAllTopRatedMovies(Constants.API_KEY)

    suspend fun getMovieDetail(movieId: Int): Response<Result> =
        movieInterface.getMoviesDetails(movieId, Constants.API_KEY)


}