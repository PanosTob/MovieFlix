package com.panostob.movieflix.di.movies

import com.panostob.movieflix.BuildConfig
import com.panostob.movieflix.data.movies.datasource.MoviesDataSource
import com.panostob.movieflix.data.movies.repository.MoviesRepositoryImpl
import com.panostob.movieflix.di.qualifier.MoviesOkHttpClient
import com.panostob.movieflix.domain.movies.repository.MoviesRepository
import com.panostob.movieflix.framework.movies.MoviesApi
import com.panostob.movieflix.framework.movies.datasource.MoviesDataSourceImpl
import com.panostob.movieflix.util.network.interceptor.MoviesApiInterceptor
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.ConnectionSpec
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MoviesModule {


    @Singleton
    @Provides
    @MoviesOkHttpClient
    fun provideMoviesOkHttpClient(
        moviesApiInterceptor: MoviesApiInterceptor,
        connectionSpec: ConnectionSpec
    ): OkHttpClient {
        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .connectionSpecs(listOf(connectionSpec))
            .addInterceptor(moviesApiInterceptor)

        return okHttpClient.build()
    }

    @Singleton
    @Provides
    fun provideMoviesApi(
        converterFactory: MoshiConverterFactory,
        @MoviesOkHttpClient okHttpClient: OkHttpClient
    ): MoviesApi {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.TMDB_JSON_API_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(converterFactory)
            .build()
            .create(MoviesApi::class.java)
    }
}

@Module
@InstallIn(SingletonComponent::class)
interface MoviesBindsModule {

    @Binds
    fun bindMoviesRepositoryImpl(repository: MoviesRepositoryImpl): MoviesRepository

    @Binds
    fun bindMoviesDataSourceImpl(dataSource: MoviesDataSourceImpl): MoviesDataSource
}