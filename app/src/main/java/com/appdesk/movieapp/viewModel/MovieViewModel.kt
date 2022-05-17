package com.appdesk.movieapp.viewModel

import android.util.Log
import androidx.lifecycle.*
import com.appdesk.movieapp.repository.MovieDetailsRepository
import com.appdesk.movieapp.data.Result
import com.appdesk.movieapp.data.MovieResponse
import com.appdesk.movieapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val repository: MovieDetailsRepository
) : ViewModel() {
    private val popularMovieLiveData = MutableLiveData<Resource<List<Result>>>()
    val _popularMovieLiveData: LiveData<Resource<List<Result>>> get() = popularMovieLiveData
    private val topRatedMovieLiveData = MutableLiveData<Resource<List<Result>>>()
    val _topRatedMovieLiveData: LiveData<Resource<List<Result>>> get() = topRatedMovieLiveData
    private val movieDetailsLiveData = MutableLiveData<Resource<Result>>()
    val _movieDetails: LiveData<Resource<Result>> get() = movieDetailsLiveData

    // postValue() & setValue() difference
    fun getPopularMovies() = viewModelScope.launch {
        withContext(Dispatchers.IO){
            popularMovieLiveData.postValue(Resource.Loading())
            try {
                val popularMovie = repository.getAllPopularMovies()
                Log.d("TAG", popularMovie.toString())
                if (popularMovie.isSuccessful) {
                    popularMovieLiveData.postValue(Resource.Success(popularMovie.body()!!.results))
                    Log.d("TAG", popularMovie?.body()?.results?.size.toString())
                }
                else{
                    popularMovieLiveData.postValue(Resource.Error(popularMovie.message()))
                }
            }
            catch (e:Exception){
                Log.d("TAG", e.toString())
                popularMovieLiveData.postValue(e.message?.let { error->
                    Resource.Error(error)
                })
            }
        }
    }

    fun getTopRatedMovies() = viewModelScope.launch {
        withContext(Dispatchers.IO){
            topRatedMovieLiveData.postValue(Resource.Loading())
            try {
                val topRatedMovie = repository.getAllTopRatedMovies()
                if (topRatedMovie.isSuccessful) {
                    topRatedMovieLiveData.postValue(Resource.Success(topRatedMovie.body()!!.results))
                }
                else{
                    topRatedMovieLiveData.postValue(Resource.Error(topRatedMovie.message()))
                }
            }
            catch (e:Exception){
                topRatedMovieLiveData.postValue(e.message?.let { error->
                    Resource.Error(error)
                })
            }
        }
    }

    fun getMoviesDetails(movieId : Int) = viewModelScope.launch {
        withContext(Dispatchers.IO){
            movieDetailsLiveData.postValue(Resource.Loading())
            try {
                val movieDetails = repository.getMovieDetail(movieId)
                if (movieDetails.isSuccessful) {
                    movieDetailsLiveData.postValue(Resource.Success(movieDetails.body()!!))
                }
                else{
                    movieDetailsLiveData.postValue(Resource.Error(movieDetails.message()))
                }
            }
            catch (e:Exception){
                movieDetailsLiveData.postValue(e.message?.let { error->
                    Resource.Error(error)
                })
            }
        }
    }

}