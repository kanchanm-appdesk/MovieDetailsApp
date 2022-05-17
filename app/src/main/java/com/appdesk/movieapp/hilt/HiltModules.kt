package com.appdesk.movieapp.hilt

import com.appdesk.movieapp.remote.MovieInterface
import com.appdesk.movieapp.repository.MovieDetailsRepository
import com.appdesk.movieapp.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@InstallIn(SingletonComponent::class)
@Module
object HiltModules {
    @Provides
    fun provideRetrofitInterface(): MovieInterface{
        return Retrofit.Builder().baseUrl(Constants.BASE_URL).addConverterFactory(
            GsonConverterFactory.create()
        ).build().create(MovieInterface::class.java)
    }

    @Provides
    fun provideRepository(movieInterface: MovieInterface): MovieDetailsRepository {
        return MovieDetailsRepository(movieInterface)
    }

}