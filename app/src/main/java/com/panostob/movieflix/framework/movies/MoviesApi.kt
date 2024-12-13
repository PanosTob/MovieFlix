package com.panostob.movieflix.framework.movies

import com.panostob.movieflix.data.movies.model.remote.RemoteMovieDetailsResponse
import com.panostob.movieflix.data.movies.model.remote.RemoteMovieReviewsResponse
import com.panostob.movieflix.data.movies.model.remote.RemotePopularMoviesResponse
import com.panostob.movieflix.data.movies.model.remote.RemoteSimilarMoviesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesApi {

    @GET("movie/popular?language=en-US")
    suspend fun getPopularMovies(
        @Query("page") page: Int
    ): Response<RemotePopularMoviesResponse>

    @GET("movie/{movie_id}?language=en-US&append_to_response=credits")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int
    ): Response<RemoteMovieDetailsResponse>

    @GET("movie/{movie_id}/similar?language=en-US&page=1")
    suspend fun getSimilarMovies(
        @Path("movie_id") movieId: Int
    ): Response<RemoteSimilarMoviesResponse>

    @GET("movie/{movie_id}/reviews?language=en-US&page=1")
    suspend fun getMovieReviews(
        @Path("movie_id") movieId: Int
    ): Response<RemoteMovieReviewsResponse>
}