package com.panostob.movieflix.di.movies

import com.panostob.movieflix.BuildConfig
import com.panostob.movieflix.data.movies.datasource.local.MoviesLocalDataSource
import com.panostob.movieflix.data.movies.datasource.remote.MoviesRemoteDataSource
import com.panostob.movieflix.data.movies.repository.MoviesRepositoryImpl
import com.panostob.movieflix.di.qualifier.MoviesOkHttpClient
import com.panostob.movieflix.domain.movies.repository.MoviesRepository
import com.panostob.movieflix.framework.movies.MoviesApi
import com.panostob.movieflix.framework.movies.datasource.local.AppDatabase
import com.panostob.movieflix.framework.movies.datasource.local.MoviesLocalDataSourceImpl
import com.panostob.movieflix.framework.movies.datasource.local.dao.MovieDao
import com.panostob.movieflix.framework.movies.datasource.remote.MoviesRemoteDataSourceImpl
import com.panostob.movieflix.util.network.interceptor.MoviesApiInterceptor
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.ConnectionSpec
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
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
        httpLoggingInterceptor: HttpLoggingInterceptor,
        moviesApiInterceptor: MoviesApiInterceptor,
        connectionSpec: ConnectionSpec
    ): OkHttpClient {
        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(moviesApiInterceptor)
            .connectionSpecs(listOf(connectionSpec)).apply {
                if (BuildConfig.DEBUG) {
                    addNetworkInterceptor(httpLoggingInterceptor)
                }
            }
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

    @Singleton
    @Provides
    fun provideMovieDao(appDatabase: AppDatabase): MovieDao {
        return appDatabase.movieDao()
    }
}

@Module
@InstallIn(SingletonComponent::class)
interface MoviesBindsModule {

    @Binds
    fun bindMoviesRepositoryImpl(repository: MoviesRepositoryImpl): MoviesRepository

    @Binds
    fun bindMoviesRemoteDataSourceImpl(dataSource: MoviesRemoteDataSourceImpl): MoviesRemoteDataSource

    @Binds
    fun bindMoviesLocalDataSourceImpl(dataSource: MoviesLocalDataSourceImpl): MoviesLocalDataSource
}